package com.ss;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class GoEasyTest {


    @Test
    public void testqueryaa () {

        //配置发送消息的必要配置  参数：regionHost,服务器地址(rest-hangzhou.goeasy.io),自己的appKey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-e69ae525ac0b44da9f7baba787ef6306");

        //配置发送消息  参数:管道名称（自定义）,发送内容
        goEasy.publish("小囤囤", "Hello, ss GoEasy!");
    }

    @Test
    public void testGoEasyUsers () {

        for (int i = 0; i < 10; i++) {

            Random random = new Random();
            HashMap<String, Object> map = new HashMap<>();

            map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
            map.put("boys", Arrays.asList(random.nextInt(100), random.nextInt(200), random.nextInt(390), random.nextInt(700), random.nextInt(200), random.nextInt(600)));
            map.put("girls", Arrays.asList(random.nextInt(100), random.nextInt(200), random.nextInt(390), random.nextInt(700), random.nextInt(200), random.nextInt(600)));

            //将对象转为json格式字符串
            String jsonString = JSON.toJSONString(map);

            //配置发送消息的必要配置  参数：regionHost,服务器地址(rest-hangzhou.goeasy.io),自己的appKey
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-e69ae525ac0b44da9f7baba787ef6306");

            //配置发送消息  参数:管道名称（自定义）,发送内容
            goEasy.publish("小囤囤", jsonString);

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
