package com.ksf.codecollection.util;

/**
 * 异常工具类
 * @author kangshaofei
 * @description
 * @date 2018/4/15
 **/
public class ExceptionUtil {

    /**
     * 从Exception异常链中获取指定的异常
     * @param throwable  异常类
     * @param targetClass 想要的异常类的class
     * @return
     */
    public static <T extends Throwable> T getTargetException(Class<T> targetClass,Throwable throwable){
        if(throwable==null){
            return null;
        }
        if(throwable.getClass().equals(targetClass)){
            return (T)throwable;
        }
        Throwable cause = throwable.getCause();
        if(cause==null){
            return null;
        }
        if(cause.getClass().equals(targetClass)){
            return (T)cause;
        }
        //防止递归死循环
        if(throwable.getClass().equals(cause.getClass())){
            return null;
        }
        return getTargetException(targetClass,cause);
    }



}
