package com.ksf.codecollection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @Author kangshaofei
 * @Description Spring事务测试类
 * @Date 2018/4/3
 **/
@Service
public class SpringTransactionTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    int i=0;

    /**
     * rollbackFor为RuntimeException,抛的异常为父类Exception，不回滚
     * rollbackFor为Exception,抛的异常为子类RuntimeException，回滚
     * @return
     * @throws Exception
     */
//    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class})
    public String insert() throws Exception {
        List list = jdbcTemplate.queryForList("select * from login_user");
        i++;
        jdbcTemplate.execute("insert into login_user(id,user_name,pass_word) values ("+i+",'3','r')");
        System.out.println("1"+list);
        if(1+1==2){
//            throw new RuntimeException();
            throw new Exception();
        }
        return "success";
    }


}
