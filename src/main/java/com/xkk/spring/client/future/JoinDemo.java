package com.xkk.spring.client.future;

/**
 * 功能描述：
 *
 * @Author: XKK
 * @Date: 2020/11/5 15:56
 */
public class JoinDemo {
    private static final int SLEEP_GAP = 500;

    static class WaterThread extends Thread {
        public WaterThread() {
            super("water线程");
        }
        public void run() {
            try {
                System.out.println("开始烧水");
                Thread.sleep(SLEEP_GAP);
                System.out.println("水开了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class WashThread extends Thread {
        public WashThread() {
            super("wash线程");
        }
        public void run() {
            try {
                System.out.println("开始清洗");
                Thread.sleep(1000);
                System.out.println("清洗完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        WaterThread waterThread = new WaterThread();
        WashThread washThread = new WashThread();
        waterThread.start();
        washThread.start();

        try {
            waterThread.join();
            washThread.join();

            System.out.println("可以泡茶了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("运行结束");

    }
}
