package com.ss.dao;

import com.ss.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDAO {

    //分页展示
    List<Log> showAllLog(@Param("start")Integer start, @Param("rows")Integer rows);
    //总条数
    Integer conter();

    //添加
    void insertLog(Log log);
    //删除
    void deleteLog(String id);
}
