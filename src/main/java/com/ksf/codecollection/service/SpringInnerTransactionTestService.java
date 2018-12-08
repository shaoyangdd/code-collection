package com.ksf.codecollection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author kangshaofei
 * @Description 内层事务测试类
 * @Date 2018/4/3
 **/
@Service
public class SpringInnerTransactionTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    int i=0;

    /**
     * 内层传播属性为默认时,Propagation.REQUIRED 外层抛异常时内层回滚
     * 内层传播属性为Propagation.REQUIRES_NEW时 外层抛异常时内层不回滚
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
//    @Transactional(rollbackFor = {Exception.class})
    public String insert() throws Exception {
        jdbcTemplate.execute("insert into t_user(user_id,user_name) values ("+i+++",'3')");
        return "success";
    }


}
