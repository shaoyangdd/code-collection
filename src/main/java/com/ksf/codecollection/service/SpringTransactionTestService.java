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

    @Autowired
    private SpringInnerTransactionTestService springInnerTransactionTestService;

    int i=0;

    /**
     * rollbackFor为RuntimeException,抛的异常为父类Exception，不回滚
     * rollbackFor为Exception,抛的异常为子类RuntimeException，回滚
     * rollbackFor为Exception,抛的异常为Error时，回滚
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
//    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {RuntimeException.class})
    public String insert() throws Exception {
        jdbcTemplate.execute("insert into t_user(user_id,user_name) values ("+i+++",'3')");
        if(1+1==2){
//            throw new RuntimeException();
//            throw new Exception();
            throw new Error("内存溢出！");
        }
        return "success";
    }


    /**
     * 内层传播属性为默认时,Propagation.REQUIRED 外层抛异常时内层回滚
     * 内层传播属性为Propagation.REQUIRES_NEW时 外层抛异常时内层不回滚
     * @return String
     * @throws Exception 异常
     */
//    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public String insert2() throws Exception {
        springInnerTransactionTestService.insert();
        if(1+1==2){
            throw new Exception();
        }
        return "success";
    }
}
