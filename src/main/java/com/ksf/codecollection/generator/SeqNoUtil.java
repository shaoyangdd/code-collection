package com.ksf.codecollection.generator;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式序号生成器
 * 时间 + 应用标识 + 子序号 如：(20170913213535876 19216899242 18803 000001)
 * 应用标识: IP + 进程ID
 * 时间格式: yyyyMMddHHmmssSSS
 * 子序号: 由于单位时间可能有多个序号生成，所以需要加子序号区分
 * Created by shaofeikang on 2017/4/28.
 * 自己琢磨出来的，之前不知道有雪花算法，有点像，生成的序号40多位有点长，可以根据实际情况把时间，IP，子序号长度缩短
 */
public class SeqNoUtil {

    //子序号
    private static int subSeq = 1;

    //生成上个序号的时间
    private static long lastTime = 0;

    //应用标识
    private static String appId;

    //应用标识长度
    private static int appIdLength;

    //最小长度=IP长度+进程长度+时间长度+int最大值字符串长度
    private static int minLength;

    //时间格式化器
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    static {
        try {
            //获取主机IP，去掉.
            String ip = InetAddress.getLocalHost().getHostAddress().replace(".","");
            //获取进程号
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String pid = name.split("@")[0];
            //拼成应用ID
            appId = ip + pid;
            appIdLength = appId.length();
            //计算最小长度（生成的序号40多位有点长，可以根据实际情况把时间，IP，子序号长度缩短）
            minLength = appIdLength + "yyyyMMddHHmmssSSS".length() + (Integer.MAX_VALUE+"").length();
            System.out.println("最小长度：" + minLength);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成序号
     * @param length
     * @return
     * @throws RuntimeException
     */
     public String getSeqNoBylength(int length) throws RuntimeException{

        //length总长度 >=minLength
        if(length < minLength){
            throw new RuntimeException("序号长度应大于最小长度：" + minLength);
        }

        //最终序号
        StringBuilder sequence = new StringBuilder(length);
        //左补0后的子序号字符串
        StringBuilder subStr = new StringBuilder();
        // 第一部分为时间yyyyMMddHHmmssSSS 17位
        // 第二部分为应用区分号，保证集群环境下甚至单物理器机部署多应用情况下序号不重复
        // 第三部分为1毫秒内并发序号，保证1毫秒内序号不重复，1毫秒内支持的并发数量为Integer的最大值2147483647
        synchronized (SeqNoUtil.class){
             long curTime = System.currentTimeMillis();
             if(lastTime == 0){//第一个序号生成1
                 subSeq = 1;
             }else{//第二个序号及以后的序号
                 if(curTime - lastTime > 1)//1毫秒后重置
                     subSeq = 1;
                 else
                     subSeq ++;//1毫秒内的递增子序号
             }
            lastTime = curTime;
            int seqLength = (subSeq + "").length();
            int addLength = length - 17 - appIdLength  - seqLength;//yyyyMMddHHmmssSSS 17位
            for(int i=0;i<addLength;i++){
                subStr.append("0");
            }
            subStr.append(subSeq);
            sequence.append(sdf.format(new Timestamp(curTime)));
        }
        return  sequence.append(appId).append(subStr).toString();
    }

    /**
     * 生成序号测试
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        int count = 10000;//生成个数

        int length = 43;//生成长度

        SeqNoUtil sqNoUtil = new SeqNoUtil();

        Set set = new HashSet<String>();

        final CountDownLatch countDownLatch = new CountDownLatch(count);

        System.out.println("开始生成："+ System.currentTimeMillis());

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            threadList.add(new Thread(() -> {
                String s = sqNoUtil.getSeqNoBylength(length);

                synchronized (sqNoUtil){
                    set.add(s);
                    System.out.println(s);
                }

                countDownLatch.countDown();
            }));
        }

        long start = System.currentTimeMillis();

        for (Thread thread:threadList) {
            thread.start();
        }

        countDownLatch.await();

        long end = System.currentTimeMillis();

        System.out.println("线程生成完,用时：" + new BigDecimal(end - start).divide(new BigDecimal(1000)).toEngineeringString() + "秒");

        System.out.println("生成数量：" + set.size());

    }
}