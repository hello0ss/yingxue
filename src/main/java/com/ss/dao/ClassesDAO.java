package com.ss.dao;

import com.ss.entity.Classes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassesDAO {

    //分页展示
    List<Classes> showFatherClasses(@Param("start")Integer start,@Param("rows")Integer rows);
    List<Classes> showSonClasses(@Param("start")Integer start,@Param("rows")Integer rows,@Param("id")String id);
    //总条数
    Integer conterFather();
    Integer conterSon(String id);

    Classes showClasses(String id);

    //添加
    void insertClasses(Classes classes);
    //修改
    void updateClasses(Classes classes);
    //删除
    void deleteClasses(String id);
}
