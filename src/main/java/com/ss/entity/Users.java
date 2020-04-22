package com.ss.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ExcelTarget(value = "users")
public class Users implements Serializable {

    @Excel(name = "ID")
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "头像",type = 2,width = 40,height = 30)
    /*@ExcelIgnore   //跳过该字段不导出*/
    private String img;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "注册时间",format = "yyyy年MM月dd",width = 30)
    private Date registerday;

}