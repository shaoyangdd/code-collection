package com.ksf.codecollection.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author kangshaofei
 * @Description 多线程处理list
 * @Date 2018/2/8
 **/
public class ThreadProcessListTest {

    public static int THREAD_SIZE = 5;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<String> list = new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        list.add("k");
        ExecutorService executorService = Executors.newFixedThreadPool(5);



        List<String> resultList = Collections.synchronizedList(new ArrayList<>());

        List<Future<List<String>>> futureList = Collections.synchronizedList(new ArrayList<>());

        int listSize = list.size();

        if(listSize==0){
            System.out.println("listsize为0");
            return;
        }else if(listSize<THREAD_SIZE){
            System.out.println("listsize小于线程数");
            for(int i=0;i<listSize;i++){
                List<String> singleList = new ArrayList<>();
                singleList.add(list.get(i));
                futureList.add(executorService.submit(new ProcessRunable(singleList)));
            }
            for(Future<List<String>> future:futureList){
                resultList.addAll(future.get());
            }
        }else if(listSize>=THREAD_SIZE){
            System.out.println("listsize不小于线程数");
            int partSize = listSize / THREAD_SIZE;
            int partSize2 = listSize / (THREAD_SIZE -1);
            int yu = listSize % THREAD_SIZE;
            if(yu ==0){
                System.out.println("可以整除");
                for(int i=0;i<listSize;i+=partSize){
                    futureList.add(executorService.submit(new ProcessRunable(list.subList(i,i+partSize))));
                }
            }else{
                System.out.println("不可以整除");
                for(int i=0;i<(THREAD_SIZE-1);i++){
                    futureList.add(executorService.submit(new ProcessRunable(list.subList(i*partSize2,(i+1)*partSize2))));
                }
                futureList.add(executorService.submit(new ProcessRunable(list.subList((THREAD_SIZE-1)*partSize2,listSize))));
            }
            for(Future<List<String>> future:futureList){
                resultList.addAll(future.get());
            }
        }

        System.out.println("我要shutdown了");
        executorService.shutdown();
        for(String s:resultList){
            System.out.println("S===="+s);
        }

    }

    static class ProcessRunable implements Callable<List<String>>{

        private List<String> list;

        public ProcessRunable(List<String> list) {
            this.list = list;
        }

        @Override
        public List<String> call() throws Exception {
            List<String> newStringList = new ArrayList<>();
            for(String string:list){
                newStringList.add(string + "2");
                System.out.println(Thread.currentThread().getName() + (string + "2"));
            }
            return newStringList;
        }
    }

}
