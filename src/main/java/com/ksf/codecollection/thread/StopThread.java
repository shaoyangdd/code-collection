package com.ksf.codecollection.thread;

/**
 * @Author kangshaofei
 * @Description 线程安全终止demo
 * @Date 2017/12/7
 **/
public class StopThread implements Runnable{


    private volatile boolean run = true;

    @Override
    public void run() {

        int i = 0;

        while(run){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("线程运行中i = " + i++);
        }

        System.out.println("线程已安全退出");

    }

    public void setRun(boolean run) {
        this.run = run;
    }


    public static void main(String[] args) throws InterruptedException {
        StopThread runnable = new StopThread();
        Thread thread = new Thread(runnable);
        thread.start();

        //先让起的thread线程运行10秒
        Thread.sleep(10000);

        //终止thread线程
        runnable.setRun(false);
        System.out.println("停止标志设置完成");

    }
}
