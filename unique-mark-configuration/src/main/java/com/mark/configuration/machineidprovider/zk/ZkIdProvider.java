package com.mark.configuration.machineidprovider.zk;

import com.mark.configuration.ConfigImp;
import com.mark.utils.IpUtil;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/6 10:55
 * @QQ: 85104982
 */
public class ZkBase {
    private ConfigImp configImp;
    private RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000,3);
    private CuratorFramework client;
    private String ip= IpUtil.getHostIp();
    private Map<String,Long> ipMap;

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

    public void zkClose(){
        client.close();
    }
    public void registMachine()  {
        createNode(ip+"-");
    }
    public Map<String,Long> getAllIp(){
        List<String> list=getAllNode();
        return list.stream()
                .map(l->l.split("-"))
                .collect(Collectors.toMap(
                        l->l[0], l->Long.parseLong(l[1])
                ));
    }

    @Override
    public long getMachineId(String ip) {
        ipMap=getAllIp();
        return ipMap.get(ip);
    }

    public void createNode(String path) {
        try {
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(path);
        } catch (Exception e) {

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

    public List<String> getAllNode() {
        try {
            return client.getChildren().forPath("/");
        } catch (Exception e) {
            throw new RuntimeException("获取子节点出错"+e);
        }
    }

    public ZkBase(ConfigImp configImp) {
        this.configImp = configImp;
    }

}
