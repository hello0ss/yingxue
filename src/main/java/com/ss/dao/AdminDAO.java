package com.ss.dao;

import com.ss.entity.Admin;

public interface AdminDAO {

    Admin queryByName(String name);
}
