package com.ss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testQuery () {

        redisTemplate.opsForValue().set("aa","呵呵");

        System.out.println(redisTemplate.opsForValue().get("aa"));

        stringRedisTemplate.expire("aa",100000000, TimeUnit.SECONDS);
        System.out.println(stringRedisTemplate.opsForValue().get("aa"));

    }


}
