package com.ss.service;

import com.ss.entity.Classes;

import java.util.HashMap;

public interface ClassesService {

    HashMap<String,Object> queryFatherClasses(Integer page,Integer rows);
    HashMap<String,Object> querySonClasses(Integer page,Integer rows,String id);

    void insertClasses(Classes classes,String ssname);
    void updateClasses(Classes classes);
    HashMap<String,Object> deleteClasses(String id);
}
