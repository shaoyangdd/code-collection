package com.ksf.codecollection.thread;

/**
 * @Author kangshaofei
 * @Description 内存可见性测试
 * @Date 2017/12/7
 **/
public class MemoryView implements Runnable{

    /**
     * 运行标志，不加volatile会死循环下去
     */
    private boolean run = true;

    /**
     * 运行标志，加volatile关键字会正常退出
     */
//    private volatile boolean run = true;

    @Override
    public void run() {

        while(run){
        }

        System.out.println("线程已安全退出");

    }

    public void setRun(boolean run) {
        this.run = run;
    }


    public static void main(String[] args) throws InterruptedException {
        MemoryView runnable = new MemoryView();
        Thread thread = new Thread(runnable);
        thread.start();

        //先让起的thread线程运行10秒
        Thread.sleep(3000);

        //终止thread线程
        runnable.setRun(false);
        System.out.println("停止标志设置完成");

    }
}
