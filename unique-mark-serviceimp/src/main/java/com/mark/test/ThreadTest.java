package com.mark.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 帅气的Mark
 * @Description: Mark行行好，给点注释吧！
 * @Date: Create in 2018/9/5 14:04
 * @QQ: 85104982
 */
public class ThreadTest {
    private  static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        ArrayList<Long> arrayList=new ArrayList<Long>();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(()->{
                arrayList.add((Thread.currentThread().getId()%8)+1);
                System.out.println((Thread.currentThread().getId()%8)+1);
            });
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("总长"+arrayList.size());
        System.out.println("1个数:"+arrayList.stream().filter(l -> l==1).count());


    }
}
