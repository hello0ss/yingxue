package com.ss.service;

import com.ss.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

public interface UsersService {

    //分页展示
    HashMap<String,Object> queryAllUsers(Integer start, Integer rows);

    //文件上传
    void uploadImgUsers(MultipartFile img,String id);

    //添加
    String insertUsers(Users users);
    //删除
    void deleteUsers(String id);
    //修改
    void updateUsers(Users users);

    //一键信息导出
    void easyPoiPortUsers();

    //用户注册时间统计
    HashMap<String,Object> queryTimeUsers();
    //用户注册地址分布
    ArrayList<Object> queryCityUsers();

}
