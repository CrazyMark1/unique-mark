package com.mark.test;

import com.mark.service.IdService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 9:33
 * @QQ: 85104982
 */
public class ListTest {
    static ApplicationContext context = new ClassPathXmlApplicationContext("spring/serviceimp.xml");
    static IdService idService = (IdService) context.getBean("idService");

    static ArrayList<Long> list=new ArrayList<Long>();
    static ArrayList<Long> list0=new ArrayList<Long>();
    static ArrayList<Long> list1=new ArrayList<Long>();
    static ArrayList<Long> list2=new ArrayList<Long>();
    static ArrayList<Long> list3=new ArrayList<Long>();
    static ArrayList<Long> list4=new ArrayList<Long>();
    static ArrayList<Long> list5=new ArrayList<Long>();
    static ArrayList<Long> list6=new ArrayList<Long>();
    static ArrayList<Long> list7=new ArrayList<Long>();
    static AtomicLong time=new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8);
        for (int i = 0; i < 8; i++) {
            final int j = i;
            new Thread(() -> {
                ArrayList listi = getList(j);
                System.out.println("线程"+j+"准备就绪");
                try {
                    cyclicBarrier.await();
                    long s=System.currentTimeMillis();
                    for (int z = 0; z < 2000; z++) {
                        listi.add(idService.getId());
                    }
                    long e=System.currentTimeMillis();
                    System.out.println("线程"+j+"完成耗时："+(e-s));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000);
        list.addAll(list0);
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list3);
        list.addAll(list4);
        list.addAll(list5);
        list.addAll(list6);
        list.addAll(list7);
        HashMap<Long,Integer>hashMap=new HashMap<Long,Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (hashMap.get(list.get(i))!=null){
                int value=hashMap.get(list.get(i));
                value++;
                hashMap.put(list.get(i),value);
            }
            else {
                hashMap.put(list.get(i),1);
            }
        }
        System.out.println("hash"+hashMap.size());
        for (Long longs : hashMap.keySet()) {
            if (hashMap.get(longs)>1){
                System.out.println("出错");
                System.out.println(longs);
            }

        }


    }

    private static ArrayList getList(int j) {
        switch (j) {
            case 0:
                return list0;
            case 1:
                return list1;
            case 2:
                return list2;
            case 3:
                return list3;
            case 4:
                return list4;
            case 5:
                return list5;
            case 6:
                return list6;
            case 7:
                return list7;
            default:
                throw new RuntimeException("11");
        }
    }
}
