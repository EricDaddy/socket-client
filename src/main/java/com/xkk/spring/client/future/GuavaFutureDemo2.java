package com.xkk.spring.client.future;

import java.util.concurrent.Callable;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/18 11:31
 * 有一个总任务A，分解为A1，A2，只要其中一个任务失败，快速取消所有任务
 */
public class GuavaFutureDemo2 {

    //任务A
    static class WaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("开始烧水");
                Thread.sleep(1000);
                System.out.println("烧水完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        }
    }

    //任务B
    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("开始清洗");
                Thread.sleep(1000);
                System.out.println("清洗完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
