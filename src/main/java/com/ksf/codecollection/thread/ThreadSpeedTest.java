package com.ksf.codecollection.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author kangshaofei
 * @Description 多线程性能测试
 * @Date 2018/2/7
 **/
public class ThreadSpeedTest {

    static List<User> list = new ArrayList<>();

    static {
        for(int i=0;i<1000;i++){
            User user = new User();
            user.setId(i);
            user.setName("name"+i);
            list.add(user);
        }
    }



    public static void main(String[] args) throws InterruptedException {

//        long start = System.currentTimeMillis();
//        for(User user:list){
//            user.setName(user.getId()+"");
//            user.setId(user.getId()-1);
//            Thread.sleep(10);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start)+"ms");



        List<User> half1 = list.subList(0,list.size()/2);

        List<User> half2 = list.subList(list.size()/2,list.size());
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        long start = System.currentTimeMillis();
        executor.submit(new Process(half1,countDownLatch));
        executor.submit(new Process(half2,countDownLatch));
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)+"ms");


    }

    static class Process implements Runnable{

        List<User> list;

        CountDownLatch countDownLatch;

        public Process(List<User> list,CountDownLatch countDownLatch){
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for(User user:list){
                user.setName(user.getId()+"");
                user.setId(user.getId()-1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            System.out.println(user.getName()+"---"+user.getId());
            }
            System.out.println(Thread.currentThread().getId()+" complete");
            countDownLatch.countDown();
        }
    }




  static class User{

        private int id;

        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
