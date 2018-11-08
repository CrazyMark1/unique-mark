package com.mark.test;

import com.mark.service.IdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/4 17:43
 * @QQ: 85104982
 */
public class ConcurrentTest {
    static ApplicationContext context = new ClassPathXmlApplicationContext("spring/serviceimp.xml");
    static IdService idService = (IdService) context.getBean("idService");
    static ConcurrentHashMap map = new ConcurrentHashMap();
    static ConcurrentHashMap map0 = new ConcurrentHashMap();
    static ConcurrentHashMap map1 = new ConcurrentHashMap();
    static ConcurrentHashMap map2 = new ConcurrentHashMap();
    static ConcurrentHashMap map3 = new ConcurrentHashMap();
    static ConcurrentHashMap map4 = new ConcurrentHashMap();
    static ConcurrentHashMap map5 = new ConcurrentHashMap();
    static ConcurrentHashMap map6 = new ConcurrentHashMap();
    static ConcurrentHashMap map7 = new ConcurrentHashMap();






    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8);
        ConcurrentHashMap mapmark=new ConcurrentHashMap();
        for (int l = 0; l < 100; l++) {
            for (int i = 0; i < 8; i++) {
                final int j = i;
                new Thread(() -> {
                    ConcurrentHashMap mapi = getMap(j);
                    try {
                        cyclicBarrier.await();
                        for (int z = 0; z < 200000; z++) {
                            mapi.put(idService.getId(), 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            Thread.sleep(3000);
            map.putAll(map0);
            map.putAll(map1);
            map.putAll(map2);
            map.putAll(map3);
            map.putAll(map4);
            map.putAll(map5);
            map.putAll(map6);
            map.putAll(map7);


            System.out.println(map.size());
            mapmark.put(map.size(),1);
            map.clear();
            map0.clear();
            map1.clear();
            map2.clear();
            map3.clear();
            map4.clear();
            map5.clear();
            map6.clear();
            map7.clear();
        }

        System.out.println(mapmark.size());
    }

    private static ConcurrentHashMap getMap(int j) {
        switch (j) {
            case 0:
                return map0;
            case 1:
                return map1;
            case 2:
                return map2;
            case 3:
                return map3;
            case 4:
                return map4;
            case 5:
                return map5;
            case 6:
                return map6;
            case 7:
                return map7;
            default:
                throw new RuntimeException("11");
        }
    }

}
