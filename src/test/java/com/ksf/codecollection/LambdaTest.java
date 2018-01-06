package com.ksf.codecollection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * lambda表达式测试
 * @Author kangshaofei
 * @Description
 * @Date 2018/1/6
 **/
public class LambdaTest {

    /**
     * 主要对比性能
     */
    @Test
    public void test1(){

        List<String> list = new ArrayList<>();
        for(int i=0;i<10000;i++)
            list.add(String.valueOf(i));
        //lambda表达式
        long start = System.currentTimeMillis();
//        list.parallelStream().forEach((s)->{
//            try {
//                Thread.sleep(4);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        //普通测试
		for (Object s :list){
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			s.toString();
		}
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start) +" ms");
    }


    /**
     * 主要对比性能
     */
    @Test
    public void test2(){

        List<String> list = new ArrayList<>();
        for(int i=0;i<10000;i++)
            list.add(String.valueOf(i));
        //lambda表达式
        long start = System.currentTimeMillis();
//        list.parallelStream().forEach((s)->{
//            s.toString();
//        });
        //普通测试
        for (Object s :list){
            s.toString();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start) +"  ms");
    }
}
