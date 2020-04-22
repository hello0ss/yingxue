package com.ss.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ExcelTarget(value = "aaa")
public class Aaa {

    @Excel(name = "ID",needMerge = true)
    private String id;
    @Excel(name = "老师姓名",needMerge = true)
    private String name;
    @ExcelCollection(name = "计算机学生")
    private List<Users> users;
}
