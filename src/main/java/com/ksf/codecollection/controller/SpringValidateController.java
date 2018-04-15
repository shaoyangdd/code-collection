package com.ksf.codecollection.controller;

import com.ksf.codecollection.common.ValidateInterface.*;
import com.ksf.codecollection.model.User;
import com.ksf.codecollection.service.SpringValidateService;
import com.ksf.codecollection.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author kangshaofei
 * @description Spring validation框架使用示例
 * @date 2018/4/15
 **/
@RestController
public class SpringValidateController {

    @Autowired
    SpringValidateService springValidateService;

    /**
     * 新增用户
     * 这种方式不推荐
     * 校验请求体中的User对象的字段，此校验属于insert组，只对属于insert组的注解的字段进行校验
     * 校验失败的结果在bindingResult中，@Validated注解可以加在方法里也可以加到类上
     * @param user 用户对象
     * @param bindingResult bindingResult
     */
    @PostMapping("user")
    public String addUser(@RequestBody @Validated(value = Insert.class) User user, BindingResult bindingResult){
        StringBuilder errorMessage = new StringBuilder();
        if(bindingResult.hasErrors()){
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                System.out.println(fieldError.toString());
                errorMessage.append(fieldError.toString());
                //TODO 返回校验失败的响应
            }
        }
        //TODO 校验没问题，走业务逻辑
        return errorMessage.toString();
    }

    /**
     * 将校验注解到SpringValidateService类上，SpringValidateService类校验失败会抛异常
     * 推荐使用这种方式
     * 通过以下ExceptionHandler处理异常
     * @param user
     */
    @PutMapping("user")
    public void test(@RequestBody User user){
        springValidateService.test(user);
    }

    /**
     * 处理上面校验失败的异常，返回响应码
     * @param cve
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Map handleConstraintViolationException(ConstraintViolationException cve){
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : cves) {
            System.out.println(constraintViolation.getMessage());
            errorMessage.append(constraintViolation.getMessage()).append(",");
        }
        Map map = new HashMap();
        map.put("errorCode",500);
        map.put("errorMessage",errorMessage);
        return map;
    }

    /**
     * 处理上面校验失败的异常，返回响应码
     * @param exception 异常
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Map handleException(Exception exception){
        Map<String,Object> map = new HashMap<>();
        ConstraintViolationException cve = ExceptionUtil.getTargetException(ConstraintViolationException.class,exception);
        if(cve != null){
            map = handleConstraintViolationException(cve);
        }else{
            map.put("errorCode",500);
            map.put("errorMessage","其它异常");
        }
        return map;
    }
}
