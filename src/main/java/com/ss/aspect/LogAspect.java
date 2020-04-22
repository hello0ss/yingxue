package com.ss.aspect;

import com.ss.annotation.AddLog;
import com.ss.dao.LogDAO;
import com.ss.entity.Admin;
import com.ss.entity.Log;
import com.ss.util.ImageCodeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Configuration
public class LogAspect {

    @Resource
    HttpSession session;
    @Resource
    LogDAO logDAO;

    /*
    * @After  后置通知
    * @Around 环绕通知
    * @Before 前置通知
    * */
    //@Around("@annotation(com.ss.annotation.AddLog)")
    public Object addLog (ProceedingJoinPoint joinPoint) {
        System.out.println("--------------进入日志环绕通知--------------");


        /*谁   时间   操作   是否成功*/
        //谁
        Admin admin = (Admin) session.getAttribute("admin");

        //时间
        String format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());

        //操作  哪些方法
        String name = joinPoint.getSignature().getName();

            //获取方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            //获取方法上的注解
            AddLog addLog = method.getAnnotation(AddLog.class);

            //获取注解中属性的值 value
            String value = addLog.value();

        //放行方法
        try {
            Object proceed = joinPoint.proceed();

            String message = "success";

            Log log = new Log()
                    .setId(ImageCodeUtil.getNumber(6))
                    .setAdminname(admin.getName())
                    .setDate(format)
                    .setOperation(value+"("+name+")")
                    .setStatus(message);
            /*logDAO.insertLog(log);
            System.out.println("数据库插入"+log);*/


            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            String message = "error";
            return null;
        }

    }
}
