package com.ksf.codecollection.model;

import com.ksf.codecollection.common.ValidateInterface.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户实体类
 */
public class User {

    /**
     * 主键ID
     *
     * 不能为空属于Insert和Update、Delete组，insert、update和delete时校验是否为空
     */
    @NotNull(message = "主键ID不能为空",groups = {Insert.class,Update.class,Delete.class})
    private Integer id;

    /**
     * 姓名
     * 不能为空白，包括: null,空字符串, 空格
     * 长度最大50，如果超出"报姓名超长"
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1,max=50,message = "姓名超长")
    private String name;

    /**
     * 年龄
     * 最大200，最小1
     */
    @Max(value = 200,groups = {Insert.class,Update.class},message = "这么大岁数，这TM成仙了吧？")
    @Min(value = 1,groups = {Insert.class,Update.class},message = "别闹，没这么小的人")
    private int age;


    @Pattern(regexp = "^1([34578])\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 邮件
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}