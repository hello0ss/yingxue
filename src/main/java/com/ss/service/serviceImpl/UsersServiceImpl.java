package com.ss.service.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ss.annotation.AddCache;
import com.ss.annotation.AddLog;
import com.ss.annotation.DelCache;
import com.ss.dao.UsersDAO;
import com.ss.entity.City;
import com.ss.entity.Month;
import com.ss.entity.Users;
import com.ss.service.UsersService;
import com.ss.util.ImageCodeUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Resource
    UsersDAO usersDAO;
    @Resource
    HttpServletRequest request;

    @AddCache
    @AddLog(value = "用户查询")
    @Override
    public HashMap<String,Object> queryAllUsers(Integer page, Integer rows) {
        /*
         * page: 当前页
         * rows: 每页条数
         * total: 总页数
         * records: 总条数
         * */
        //开始页数
        Integer start = (page-1)*rows;
        //当前页所展示的数据
        List<Users> list = usersDAO.showAllUsers(start, rows);
        //总条数
        Integer records = usersDAO.conter();
        //总页数
        Integer total;
        if (records%rows == 0) {
            total = records / rows;
        } else {
            total = records/rows+1;
        }

        HashMap<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("page",page);
        map.put("total",total);
        map.put("records",records);

        return map;
    }

    @DelCache
    @AddLog(value = "用户头像文件上传")
    @Override
    public void uploadImgUsers(MultipartFile img,String id) {
        //1.根据相对路径获取绝对路径
        String realPath = request.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //2获取文件名
        String filename = img.getOriginalFilename();
        String newName = new Date().getTime()+"-"+filename;

        try {
            //文件上传
            img.transferTo(new File(realPath,newName));
            //4.图片修改
            Users users = new Users();
            usersDAO.updateUsers(users.setId(id).setImg(newName));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DelCache
    @AddLog(value = "用户添加")
    @Override
    public String insertUsers(Users users) {
        String ss = ImageCodeUtil.getNumber(6);
        usersDAO.insertUsers(users.setId(ss).setStatus("1").setRegisterday(new Date()));
        return ss;
    }

    @DelCache
    @AddLog(value = "用户删除")
    @Override
    public void deleteUsers(String id) {
        usersDAO.deleteUsers(id);
    }

    @DelCache
    @AddLog(value = "用户修改")
    @Override
    public void updateUsers(Users users) {
        if (users.getImg()=="") {
            users.setImg(usersDAO.update(users.getId()).getImg());
        }
        usersDAO.updateUsers(users);
    }

    @AddLog(value = "用户信息导出Excel")
    @Override
    public void easyPoiPortUsers() {

        List<Users> list = usersDAO.queryAllUsers();
        for (Users ss : list) {
            ss.setImg("src/main/webapp/img/"+ss.getImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生表", "学生信息"), Users.class, list);

        try {
            workbook.write(new FileOutputStream(new File("D:\\BBB.xls")));
            workbook.close();
            /*map.put("message", "Excel导出成功,,文件位于D盘下");*/
        } catch (IOException e) {
            e.printStackTrace();
            /*map.put("message", "Excel导出失败");*/
        }
    }

    @AddCache
    @AddLog(value = "用户注册时间统计")
    @Override
    public HashMap<String, Object> queryTimeUsers() {

        HashMap<String,Object> map = new HashMap<>();

        List<Integer> list1 = new ArrayList<>();
        for (int i = 1;i<=12;i++){
            System.out.println(i);
            list1.add(usersDAO.queryTimeUsers("男",i));
        }

        List<Integer> list2 = new ArrayList<>();
        for (int i = 1;i<=12;i++){
            list2.add(usersDAO.queryTimeUsers("女",i));
        }

        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"));
        map.put("boys", list1);
        map.put("girls", list2);

        return map;
    }

    @AddCache
    @AddLog(value = "用户注册地址分布")
    @Override
    public ArrayList<Object> queryCityUsers() {

        ArrayList<Object> list = new ArrayList<>();

        list.add(new Month("男",usersDAO.queryCityUsers("男")));
        list.add(new Month("女",usersDAO.queryCityUsers("女")));

        System.out.println(list);

        return list;
    }
}
