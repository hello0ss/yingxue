package com.ss.controller;

import com.ss.entity.Classes;
import com.ss.service.ClassesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("classes")
public class ClassesController {

    @Resource
    ClassesService classesService;

    @ResponseBody
    @RequestMapping("queryFatherClassesMethod")
    public HashMap<String,Object> queryFatherClassesMethod(Integer page,Integer rows) {
        System.out.println("--------------父类别分页展示---------------");

        return classesService.queryFatherClasses(page,rows);
    }
    @ResponseBody
    @RequestMapping("querySonClassesMethod")
    public HashMap<String,Object> querySonClassesMethod(Integer page,Integer rows,String id) {
        System.out.println("--------------子类别分页展示---------------");

        return classesService.querySonClasses(page,rows,id);
    }

    @ResponseBody
    @RequestMapping("ClassesMethod")
    public Object ClassesMethod(Classes classes,String oper,String ssname){
        System.out.println(classes);
        System.out.println(oper);

        if (oper.equals("add")) {
            System.out.println("--------------类别添加---------------");
            classesService.insertClasses(classes,ssname);
        } else if (oper.equals("edit")) {
            System.out.println("--------------类别修改---------------");
            classesService.updateClasses(classes);
        } else if (oper.equals("del")) {
            System.out.println("--------------类别删除---------------");

            return classesService.deleteClasses(classes.getId());
        }
        return "";
    }
}
