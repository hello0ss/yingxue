package com.ss.service.serviceImpl;

import com.ss.dao.AdminDAO;
import com.ss.entity.Admin;
import com.ss.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    HttpSession session;

    @Resource
    AdminDAO adminDAO;

    @Override
    public HashMap<String,Object> loginAdmin(Admin admin, String code) {

        HashMap<String,Object> map = new HashMap<>();

        Admin ad = adminDAO.queryByName(admin.getName());
        String imageCode = (String) session.getAttribute("imageCode");

        //判断该管理员是否存在
        if (ad != null) {
            //判断验证码是否正确
            if (code.equals(imageCode)) {
                //判断密码是否正确
                if (ad.getPassword().equals(admin.getPassword())) {
                    //存储用户信息
                    session.setAttribute("admin",ad);

                    map.put("status","200");
                    map.put("message","登陆成功");
                } else {
                    map.put("status","400");
                    map.put("message","密码输入错误");
                }
            } else {
                map.put("status","400");
                map.put("message","验证码错误");
            }
        } else {
            map.put("status","400");
            map.put("message","该用户不存在");
        }

        return map;
    }
}
