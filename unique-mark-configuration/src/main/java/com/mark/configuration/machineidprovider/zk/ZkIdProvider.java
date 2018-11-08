package com.mark.configuration.machineidprovider.zk;

import com.mark.configuration.ConfigImp;
import com.mark.configuration.machineidprovider.MachineIdProvider;
import com.mark.utils.IpUtil;
import com.mark.utils.TimeUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/6 10:55
 * @QQ: 85104982
 */
public class ZkIdProvider implements MachineIdProvider{
    private ConfigImp configImp;
    private RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000,3);
    private CuratorFramework client;
    private String ip= IpUtil.getHostIp();
    private Map<String,Long> ipMap;
    private long machineId;

    public ZkIdProvider(ConfigImp configImp) {
        this.configImp = configImp;
        zkStart();
    }

    public void zkStart(){
        client=CuratorFrameworkFactory.builder()
                .connectString(configImp.getZk())
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("unique-mark")
                .build();
        client.start();
    }
    private void submitLocalTimestamp(){
        new Thread(()->{
            while (true){
                setZkTimestamp();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void zkClose(){
        client.close();
    }

    public void registMachine() throws Exception {
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath("/"+ip+"-", new Long(TimeUtil.getTimestamp(configImp.getTimeType())).toString().getBytes());
    }

    private Map<String,Long> getAllIp() throws Exception {
        List<String> list=getAllNode();
        return list.stream()
                .map(l->l.split("-"))
                .collect(Collectors.toMap(
                        l->l[0], l->Long.parseLong(l[1])
                ));
    }
    private boolean checkIpExist(Map<String,Long> map){
        return map.containsKey(ip);
    }

    @Override
    public long getMachineId() {
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        FutureTask<Long> futureTask=new FutureTask<Long>(()->{
            try {
                ipMap=getAllIp();
                if (!checkIpExist(ipMap)){
                    registMachine();
                    ipMap=getAllIp();
                    machineId = ipMap.get(ip);
                    TimeUtil.validateZkTimestamp(TimeUtil.getTimestamp(configImp.getTimeType()),getZkTimestamp());
                    submitLocalTimestamp();
                    persistenceId();
                }
            }catch (Exception e){
                readLocalId();
            }
            return machineId;
        });
        executorService.execute(futureTask);
        try {
            machineId=futureTask.get(10, TimeUnit.SECONDS);
            return machineId;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            readLocalId();
            return machineId;
        }
    }

    public void deleteNode(String path) {
        try {
            client.delete().deletingChildrenIfNeeded()
                    .forPath(path);
        } catch (Exception e) {
            throw new RuntimeException("删除节点出错"+e);
        }
    }

    public long getZkTimestamp(){
        try {
            return Long.parseLong(new String(client.getData().forPath(ipToNode())));
        } catch (Exception e) {
            throw new RuntimeException("获取时间失败");
        }
    }

    private void setZkTimestamp(){
        try {
            client.setData().forPath(ipToNode(),new Long(TimeUtil.getTimestamp(configImp.getTimeType())).toString().getBytes());
        } catch (Exception e) {
            throw new RuntimeException("设置时间出错"+e);
        }
    }

    private List<String> getAllNode() throws Exception {
            return client.getChildren().forPath("/");
    }
    private String ipToNode(){
        return "/"+ip+"-"+String.format("%010d",machineId);
    }

    private void persistenceId(){
        String path=this.getClass().getResource("/").getPath()+ip;
        try {
            FileWriter writer=new FileWriter(path);
            writer.write(((int) machineId));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readLocalId(){
        String path=this.getClass().getResource("/").getPath()+ip;
        try {
            FileReader reader=new FileReader(path);
            machineId=reader.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("不能连接远程服务器，本地也没有Ip缓存，请切换别的Ip获取模式");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
