package com.ss.service;

import java.util.HashMap;

public interface LogService {

    //分页展示
    HashMap<String,Object> showAllLog(Integer page, Integer rows);
}
