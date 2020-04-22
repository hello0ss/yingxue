package com.ss.service;

import com.ss.entity.Admin;

import java.util.HashMap;

public interface AdminService {

    HashMap<String,Object> loginAdmin(Admin admin, String code);
}
