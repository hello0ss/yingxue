package com.ss.dao;

import com.ss.entity.City;
import com.ss.entity.Users;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UsersDAO {

    //分页展示
    List<Users> showAllUsers(@Param("start")Integer start,@Param("rows")Integer rows);
    //总条数
    Integer conter();

    //添加
    void insertUsers(Users users);
    //删除
    void deleteUsers(String id);
    //修改
    void updateUsers(Users users);
    Users update(String id);

    //导出信息进Excel表格
    List<Users> queryAllUsers();

    //用户注册时间统计
    Integer queryTimeUsers(@Param("sex")String sex,@Param("month")Integer month);

    //用户注册地址分布
    List<City> queryCityUsers(String sex);
}