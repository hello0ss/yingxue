package com.ss.service.serviceImpl;

import com.ss.dao.LogDAO;
import com.ss.entity.Log;
import com.ss.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogDAO logDAO;

    @Override
    public HashMap<String, Object> showAllLog(Integer page, Integer rows) {
        /*
         * page: 当前页
         * rows: 每页条数
         * total: 总页数
         * records: 总条数
         * */
        //开始页数
        Integer start = (page-1)*rows;
        //当前页所展示的数据
        List<Log> list = logDAO.showAllLog(start, rows);
        //总条数
        Integer records = logDAO.conter();
        //总页数
        Integer total = records%rows==0?records/rows:records/rows+1;

        HashMap<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);

        return map;
    }
}
