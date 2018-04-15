package com.ksf.codecollection.service;

import com.ksf.codecollection.model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author kangshaofei
 * @description Spring校验服务类
 * @date 2018/4/15
 **/
@Validated
@Service
public class SpringValidateService {

    /**
     * 通过类进行校验
     * @param user
     */
    public void test(@Valid User user){
        System.out.printf("aaa");
    }

}
