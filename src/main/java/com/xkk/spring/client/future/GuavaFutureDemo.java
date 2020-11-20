package com.xkk.spring.client.future;

import com.google.common.util.concurrent.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/5 17:16
 */
@Slf4j
public class GuavaFutureDemo {
    private static final int SLEEP_GAP = 3000;


    static class WaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("开始烧水");
                Thread.sleep(SLEEP_GAP);
                System.out.println("烧水完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        }
    }

    static class WashJob implements Callable<Boolean> {

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
            return true;
        }
    }
    
    

    public static void main(String[] args) {
        System.out.println("主线程开始");

        //烧水的业务逻辑实例
        Callable<Boolean> waterJob = new WaterJob();
        //清洗的业务逻辑实例
        Callable<Boolean> washJob = new WashJob();

        //创建java线程池
        ExecutorService jpool = Executors.newFixedThreadPool(10);
        //包装guava线程池
        ListeningExecutorService gpool = MoreExecutors.listeningDecorator(jpool);

        //提交烧水的业务逻辑实例，到线程池中获取异步任务
        ListenableFuture<Boolean> waterFuture = gpool.submit(waterJob);
        //绑定烧水的异步回调
        Futures.addCallback(waterFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                System.out.println("烧水的异步回调结果成功了" + aBoolean);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("烧水的异步回调结果失败了了");
            }
        });

        //提交清洗的业务逻辑实例，到线程池中获取异步任务
        ListenableFuture<Boolean> washFuture = gpool.submit(washJob);
        //绑定清洗的异步回调
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                System.out.println("清洗的异步回调结果成功了" + aBoolean);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("清洗的异步回调结果失败了了");
            }
        });

        System.out.println("主线程已经走到结束的地方了");
    }

}
