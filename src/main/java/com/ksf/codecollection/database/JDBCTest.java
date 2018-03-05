package com.ksf.codecollection.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author kangshaofei
 * @Description JDBC测试类
 * @Date 2018/1/29
 **/
public class JDBCTest {

    static String url="jdbc:mysql://127.0.0.1:3306/28world_login";

    static String user="root";

    static String password="82993341";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into login_user(id,user_name,pass_word) VALUES (?,?,?)");
        long start = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            preparedStatement.setInt(1,i);
            preparedStatement.setString(2,""+i);
            preparedStatement.setString(3,"bbb");
            if(i%100==0)
            preparedStatement.executeBatch();
        }
        preparedStatement.executeBatch();

        long end = System.currentTimeMillis();
        BigDecimal a = new BigDecimal(end-start).divide(new BigDecimal(10000));
        System.out.println("总耗时：" + (end - start) + "ms" + "平均：" + a + "ms");

        preparedStatement.close();
        connection.close();
    }




}
