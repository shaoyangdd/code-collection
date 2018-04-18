package com.ksf.codecollection.controller;

import com.ksf.codecollection.service.SpringTransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试事务控制器
 *
 * @Author kangshaofei
 * @Description
 * @Date 2018/4/2
 **/
@RestController
public class TransactionTestController {

    @Autowired
    private SpringTransactionTestService springTransactionTestService;

    @PostMapping("/ha")
    public String testTransaction() throws Exception {
        try{
            springTransactionTestService.insert();
        }catch (Exception e){
            System.out.printf("-------------------");
        }
        return "success";

    }

}
