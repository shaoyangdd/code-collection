package com.ksf.codecollection.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author kangshaofei
 * @Description 数据库连接池测试
 * @Date 2018/1/28
 **/
public class ConnectionPool {

    /**
     * 数据库连接池
     */
    private Queue<Connection> connectionList = new LinkedList<>();

    public ConnectionPool() throws SQLException {
        for(int i=0;i<10;i++){
            connectionList.offer(DriverManager.getConnection("jdbc:mysql://localhost:3306/front_system","root","123456"));
        }
    }

    /**
     * 获取连接
     * @param mills 超时时间
     * @return
     */
    public Connection getConnection(long mills){
        synchronized (connectionList){
            if(mills<=0){
                return connectionList.poll();
            }else{
                long remain = mills;
                long timeout = System.currentTimeMillis() + mills;
                while(connectionList.isEmpty() && remain > 0){
                    try {
                        connectionList.wait(remain);
                        //如果被notify需要重新设置remain,然后再次进入while判断是否为空
                        remain = timeout - System.currentTimeMillis();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                return connectionList.poll();
            }
        }
    }

    /**
     * 释放连接
     * @param connection 连接
     */
    public void releaseConnection(Connection connection){

        synchronized (connectionList){
            connectionList.offer(connection);
            //通知其它消费线程来取连接
            connectionList.notifyAll();
        }

    }

    public static void main(String[] args) throws SQLException, InterruptedException {

        ConnectionPool connectionPool = new ConnectionPool();
        List<Thread> getThread = new ArrayList<>();
        System.out.println("前边10个线程先用了，每个线程用20秒");
        for(int i=0;i<10;i++){
            Thread thread = new Thread(() -> {
                Connection connection = connectionPool.getConnection(1000);
                System.out.println(Thread.currentThread().getId()+"获取到connection:" + connection);
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectionPool.releaseConnection(connection);
                System.out.println(Thread.currentThread().getId()+"释放connection:" + connection);
            });
            getThread.add(thread);
        }


        for(Thread thread: getThread){
            thread.start();
        }

        Thread.sleep(2000);

        List<Thread> getThread2 = new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread thread = new Thread(() -> {
                long start = System.currentTimeMillis();
                //这个超时时间可以改成20秒测试notify
                Connection connection = connectionPool.getConnection(10000);
                long end = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getId()+"后面线程获取到connection:" + connection + "耗时："+(end - start) + "ms");
            });
            getThread2.add(thread);
        }

        System.out.println("后面10个线程启来要连接，超时10秒");
        for(Thread thread: getThread2){
            thread.start();
        }
    }


}
