package com.mark.test;

import com.mark.configuration.machineidprovider.zk.ZkIdProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/6 11:18
 * @QQ: 85104982
 */
public class ZkTest {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("spring/configimp.xml");
        ZkIdProvider zkIdProvider= (ZkIdProvider) context.getBean("zkIdProvider");
//        zkIdProvider.deleteNode("/");
        System.out.println(zkIdProvider.getMachineId());
        System.out.println(zkIdProvider.getZkTimestamp());
//        zkIdProvider.submitLocalTimestamp();
        while (true){
            System.out.println(zkIdProvider.getZkTimestamp());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        zkIdProvider.checkNode();
    }
}
