package com.ss;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ss.entity.Aaa;
import com.ss.entity.Users;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PoiTest {

    @Test
    public void testqueryss () {

        //创建Excet文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作簿 (工作簿名称)
        HSSFSheet sheet = workbook.createSheet("用户信息表1");

        //创建行 (下标从0开始)
        HSSFRow row = sheet.createRow(0);

        //创建单元格 (单元格下标从0开始)
        HSSFCell cell = row.createCell(0);

        //设置单元格内容
        cell.setCellValue("这是第一行第一个单元格内容");

        //导处单元格
        try {
            workbook.write(new FileOutputStream(new File("D://AAA.xls")));

            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExports () {

        //创建集合
        Users users1 = new Users("1", "张三", "121212", "aaa", "aaa","aaa", "aaa", "12qwaszx", "正常", new Date());
        Users users2 = new Users("2", "张三", "121212", "aaa", "aaa","aaa", "aaa", "12qwaszx", "正常", new Date());
        Users users3 = new Users("3", "张三", "121212", "aaa", "aaa","aaa", "aaa", "12qwaszx", "正常", new Date());
        Users users4 = new Users("4", "张三", "121212", "aaa", "aaa","aaa", "aaa", "12qwaszx", "正常", new Date());
        Users users5 = new Users("5", "张三", "121212", "aaa", "aaa","aaa", "aaa", "12qwaszx", "正常", new Date());
        List<Users> list = Arrays.asList(users1, users2, users3, users4, users5);

        //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作表
        HSSFSheet sheet = workbook.createSheet("用户信息2");

        //设置指定单元格宽度
        sheet.setColumnWidth(7,19*256);

        //设置标题34样式
        HSSFFont fonts = workbook.createFont();
        fonts.setBold(true); //字体加粗
        fonts.setColor(Font.COLOR_RED); //字体颜色
        fonts.setColor(IndexedColors.GREEN.getIndex()); //
        fonts.setFontHeightInPoints((short)20); //字体大小
        fonts.setFontName("楷体"); //字体类型
        fonts.setItalic(true); //字体样式(斜体)
        fonts.setUnderline(FontFormatting.U_SINGLE); //字体样式(下划线)

        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setFont(fonts);
        cellStyle3.setAlignment(HorizontalAlignment.CENTER); //文字居中

        //创建标题行
        HSSFRow row = sheet.createRow(0);

        //设置指定行高度
        row.setHeight((short)400);

        //创建标题单元格
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(cellStyle3);
        cell.setCellValue("学生信息表");

        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));

        //创建目录行
        HSSFRow row1 = sheet.createRow(1);
        String[] title = {"ID","用户名","手机号","头像","签名","微信","状态","注册时间"};

        //设置字体样式
        HSSFFont font = workbook.createFont();
        font.setBold(true); //字体加粗

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); //文字居中


        //设置日期格式样式
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setDataFormat(workbook.createDataFormat().getFormat("yyyy年MM月dd日"));

        //处理单元格
        HSSFCell cell1 = null;
        for (int i = 0;i < title.length;i++) {
            //根据单元格下标处理单元格内容
            cell1 = row1.createCell(i);
            cell1.setCellValue(title[i]);

            //标题行使用样式
            cell1.setCellStyle(cellStyle);
        }

        //创建数据行
        HSSFRow row2 = null;
        for (int i = 0;i < list.size();i++) {
            //遍历一次创建一行
            row2 = sheet.createRow(i + 2);

            //每行对应放的数据
            row2.createCell(0).setCellValue(list.get(i).getId());
            row2.createCell(1).setCellValue(list.get(i).getUsername());
            row2.createCell(2).setCellValue(list.get(i).getPhone());
            row2.createCell(3).setCellValue(list.get(i).getImg());
            row2.createCell(3).setCellValue(list.get(i).getSex());
            row2.createCell(4).setCellValue(list.get(i).getSign());
            row2.createCell(3).setCellValue(list.get(i).getCity());
            row2.createCell(5).setCellValue(list.get(i).getWechat());
            row2.createCell(6).setCellValue(list.get(i).getStatus());
            row2.createCell(7).setCellValue(list.get(i).getRegisterday());
            HSSFCell cell2 = row2.createCell(7);
            cell2.setCellStyle(cellStyle2);
            cell2.setCellValue(list.get(i).getRegisterday());

        }

        //导处单元格
        try {
            workbook.write(new FileOutputStream(new File("D://AAA.xls")));

            //释放资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PoiImport () {

        try {
            //获取指定文件
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://AAA.xls")));

            //获取指定工作簿
            HSSFSheet sheet = workbook.getSheet("用户信息2");

            for (int i = 2;i < sheet.getLastRowNum()+1;i++) {

                //获取行
                HSSFRow row = sheet.getRow(i);

                //获取数据
                Users users = new Users().setId(row.getCell(0).getStringCellValue())
                        .setUsername(row.getCell(1).getStringCellValue())
                        .setPhone(row.getCell(2).getStringCellValue())
                        .setImg(row.getCell(3).getStringCellValue())
                        .setSex(row.getCell(3).getStringCellValue())
                        .setSign(row.getCell(4).getStringCellValue())
                        .setCity(row.getCell(3).getStringCellValue())
                        .setWechat(row.getCell(5).getStringCellValue())
                        .setStatus(row.getCell(6).getStringCellValue())
                        .setRegisterday(row.getCell(7).getDateCellValue());
                System.out.println(users);

            }

            //关闭资源
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testEasyPort () {

        //创建学生集合
        List<Users> list = new ArrayList<>();
        list.add(new Users("1", "张三", "121212", "C:\\Users\\86184\\Pictures\\Saved Pictures\\啊啊的壁纸\\1.jpg", "aaa", "aa", "aaa", "12qwaszx", "正常", new Date()));
        list.add(new Users("2", "张三", "121212", "src/main/webapp/img/2.jpg","aaa", "aa", "aaa", "12qwaszx", "正常", new Date()));
        list.add(new Users("3", "张三", "121212", "src/main/webapp/img/3.jpg","aaa", "aa", "aaa", "12qwaszx", "正常", new Date()));
        list.add(new Users("4", "张三", "121212", "src/main/webapp/img/4.jpeg","aaa", "aa", "aaa", "12qwaszx", "正常", new Date()));
        list.add(new Users("5", "张三", "121212", "src/main/webapp/img/5.jpg","aaa", "aa", "aaa", "12qwaszx", "正常", new Date()));

        /*//创建老师集合
        List<Aaa> aaa = new ArrayList<>();
        aaa.add(new Aaa("1","随便",list));
        aaa.add(new Aaa("2","随便",list));*/

        //                                                            参数：            标题，                 表名，    实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生表", "学生信息"), Users.class, list);

        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\86184\\Desktop\\笔记\\10__后期项目\\Day10 Poi，EasyPoi\\BBB.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TestEasyPoiInput () {

        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题行数：默认0
        params.setHeadRows(2);  //表头行数：默认1

        //获取导入数据
        try {
            List<Users> list = ExcelImportUtil.importExcel(new FileInputStream(new File("C:\\Users\\86184\\Desktop\\笔记\\10__后期项目\\Day10 Poi，EasyPoi\\BBB.xls")), Users.class, params);

            for (Users ss : list) {
                System.out.println(ss);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
