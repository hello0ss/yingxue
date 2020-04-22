package com.ss.controller;

import com.ss.entity.Admin;
import com.ss.service.AdminService;
import com.ss.service.LogService;
import com.ss.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Resource
    AdminService adminService;

    @Resource
    LogService logService;


    @RequestMapping("getImageCode")
    public void getImageCode(HttpSession session,HttpServletResponse response) {
        //获得随机字符
        String securityCode = ImageCodeUtil.getSecurityCode();
        //打印随机字符
        System.out.println("====验证码" + securityCode);
        //存入session中
        session.setAttribute("imageCode", securityCode);
        //生成图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        //将图片响应到页面
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("loginAdmin")
    @ResponseBody
    public HashMap<String, Object> loginAmdmin(Admin admin, String code) {
        System.out.println("-------------管理员登录--------------");

        HashMap<String, Object> as = adminService.loginAdmin(admin, code);
        System.out.println(as);
        return as;
    }

    @RequestMapping("logoutAdmin")
    public String logoutAdmin(HttpSession session) {
        System.out.println("-------------管理员退出--------------");

        session.removeAttribute("admin");
        return "login/login";
    }

    @ResponseBody
    @RequestMapping("showAllLogMethod")
    public HashMap<String,Object> showAllLogMethod (Integer page, Integer rows) {
        System.out.println("-------------管理员操作日志展示--------------");

        HashMap<String, Object> map = logService.showAllLog(page, rows);

        return map;
    }

}
