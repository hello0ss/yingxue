package com.ss.service.serviceImpl;

import com.ss.annotation.AddCache;
import com.ss.annotation.AddLog;
import com.ss.annotation.DelCache;
import com.ss.dao.ClassesDAO;
import com.ss.entity.Classes;
import com.ss.service.ClassesService;
import com.ss.util.ImageCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ClassesServiceImpl implements ClassesService {

    @Resource
    ClassesDAO classesDAO;

    @AddCache
    @AddLog(value = "父级类别查询")
    @Override
    public HashMap<String, Object> queryFatherClasses(Integer page, Integer rows) {
        /*
         * page: 当前页
         * rows: 每页条数
         * total: 总页数
         * records: 总条数
         * */
        //开始页数
        Integer start = (page-1)*rows;
        //每页展示数据
        List<Classes> list = classesDAO.showFatherClasses(start, rows);
        //总条数
        Integer records = classesDAO.conterFather();
        //总页数
        Integer total = records%rows==0?records/rows:records/rows+1;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",records);
        map.put("total",total);

        return map;
    }

    @AddCache
    @AddLog(value = "子级类别查询")
    @Override
    public HashMap<String, Object> querySonClasses(Integer page, Integer rows,String id) {
        /*
         * page: 当前页
         * rows: 每页条数
         * total: 总页数
         * records: 总条数
         * */
        //开始页数
        Integer start = (page-1)*rows;
        //每页展示数据
        List<Classes> list = classesDAO.showSonClasses(start, rows,id);
        //总条数
        Integer records = classesDAO.conterSon(id);
        //总页数
        Integer total = records%rows==0?records/rows:records/rows+1;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page",page);
        map.put("rows",list);
        map.put("records",records);
        map.put("total",total);

        return map;
    }

    @DelCache
    @AddLog(value = "二级类别删除")
    @Override
    public void insertClasses(Classes classes,String ssname) {

        //判断是否为一级类别
        if (ssname == null) {
            classes.setId(ImageCodeUtil.getNumber(4)).setLevel("1").setParentlevel("");
        }else {
            classes.setId(ImageCodeUtil.getNumber(4)).setLevel("2").setParentlevel(ssname);
        }
        classesDAO.insertClasses(classes);
    }

    @DelCache
    @AddLog(value = "类别修改")
    @Override
    public void updateClasses(Classes classes) {
        classesDAO.updateClasses(classes);
    }

    @DelCache
    @AddLog(value = "父级类别删除")
    @Override
    public HashMap<String, Object> deleteClasses(String id) {

        HashMap<String, Object> map = new HashMap<>();
        //获取该类别数据
        Classes classes = classesDAO.showClasses(id);

        //判断是否为1级类别
        if ("1".equals(classes.getLevel())) {
            //判断一级类别下是否存在二级类别
            if (classesDAO.showSonClasses(0,6,id).isEmpty()) {
                classesDAO.deleteClasses(id);
                map.put("message","删除一级类别成功");
            } else {
                map.put("message","该类别下还有子类别");
            }
        } else {
            classesDAO.deleteClasses(id);
            map.put("message","删除二级类别成功");
        }
        return map;
    }
}
