package com.ss.controller;

import com.ss.entity.Users;
import com.ss.service.UsersService;
import com.ss.util.AliyunSendPhoneUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UsersController {

    @Resource
    UsersService usersService;

    @ResponseBody
    @RequestMapping("queryAllMethod")
    public HashMap<String,Object> queryAllMethod(Integer page, Integer rows) {
        System.out.println("--------------用户分页展示---------------");

        return usersService.queryAllUsers(page, rows);
    }

    @ResponseBody
    @RequestMapping("UsersMethod")
    public String UsersMethod(Users users, String oper) {

        String ss = null;
        if (oper.equals("add")) {
            System.out.println("-------------用户添加------------");
            ss = usersService.insertUsers(users);
        } else if (oper.equals("edit")) {
            System.out.println("-------------用户修改------------");
            usersService.updateUsers(users);
        } else if (oper.equals("del")) {
            System.out.println("-------------用户删除------------");
            usersService.deleteUsers(users.getId());
        }
        return ss;
    }

    //文件上传
    @RequestMapping("uploadImgUsersMethod")
    public void uploadImgUsersMethod(MultipartFile img,String id){
        System.out.println("-------------文件上传---------------");
        usersService.uploadImgUsers(img,id);
    }


    //短息验证码获取
    @RequestMapping("SendPhoneMethod")
    public String SendPhoneMethod (String phone) {
        System.out.println("-------------短息验证码获取---------------");

        //获取随机数(验证码)
        String random = AliyunSendPhoneUtil.getRandom(6);
        System.out.println("------------- 验证码："+random+"---------------");

        //为指定手机发送短息
        String message = AliyunSendPhoneUtil.sendCode(phone,random);

        System.out.println(message);

        return message;
    }

    //一键导入信息
    @ResponseBody
    @RequestMapping("easyPoiUsersPort")
    public void easyPoiUsersPort () {
        System.out.println("-------------用户信息导出Excel---------------");

        usersService.easyPoiPortUsers();
    }

    @ResponseBody
    @RequestMapping("sexUsersMethod")
    public HashMap<String,Object> sexUsersMethod () {
        System.out.println("-------------展示用户注册性别统计---------------");

        return usersService.queryTimeUsers();
    }

    @ResponseBody
    @RequestMapping("cityUsersMethod")
    public ArrayList<Object> cityUsersMethod () {
        System.out.println("-------------展示用户注册地址分布---------------");

        return usersService.queryCityUsers();
    }

}
