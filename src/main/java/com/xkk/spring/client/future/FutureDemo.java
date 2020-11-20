package com.xkk.spring.client.future;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/5 16:23
 */
public class FutureDemo {
    private static final int SLEEP_GAP = 2000;

    static class WaterThread implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("开始烧水");
                Thread.sleep(SLEEP_GAP);
                System.out.println("水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    static class WashThread implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("开始清洗");
                Thread.sleep(SLEEP_GAP);
                System.out.println("清洗完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return  true;
        }
    }


    public static void main(String[] args) {
        Callable<Boolean> waterJob = new WaterThread();
        FutureTask<Boolean> waterTask = new FutureTask<>(waterJob);
        Thread waterThread = new Thread(waterTask);
        Callable<Boolean> washJob = new WashThread();
        FutureTask<Boolean> washTask = new FutureTask<>(washJob);
        Thread washThread = new Thread(washTask);
        waterThread.start();
        washThread.start();

        try {
            Boolean waterResult = waterTask.get();
            Boolean washResult = washTask.get();
            System.out.println("烧水线程返回值：" + waterResult);
            System.out.println("清洗线程返回值：" + washResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("可以喝茶了");
    }
}
