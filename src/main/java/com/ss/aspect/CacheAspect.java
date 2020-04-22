package com.ss.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

@Configuration
@Aspect
public class CacheAspect {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //@Around("execution(* com.ss.serviceimpl.*.query*(..))")
    //@Around("@annotation(com.ss.annotation.AddCache)")
    public Object addCache (ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("---------------进入缓存环绕通知-------------");

        //序列化解决乱码
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);

        StringBuilder stringBuilder = new StringBuilder();
        //获取类的全限定名
        stringBuilder.append(joinPoint.getTarget().getClass().getName());
        //获取方法名
        stringBuilder.append(joinPoint.getSignature().getName());
        //获取参数
        Object[] args = joinPoint.getArgs();
        for (Object ss : args) {
            stringBuilder.append(ss);
        }
        //拼接key
        String key = stringBuilder.toString();
        //取出key
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object ss = null;
        //在Redis中判断key是否存在
        if (stringRedisTemplate.hasKey(key)) {
            //存在 取出数据并返回结果
            ss = valueOperations.get(key);
        } else {
            //不存在 将方法放行获取结果 后返回结果
            ss = joinPoint.proceed();
            //将结果加入缓存
            valueOperations.set(key,ss);
        }
        return ss;
    }

    //@After("execution(* com.ss.serviceimpl.*.*(..)) && !execution(* com.ss.serviceimpl.*.query*(..))")
    //@After("@annotation(com.ss.annotation.DelCache)")
    public void delCache (JoinPoint joinPoint) {
        System.out.println("-------------清空缓存--------------");

        //获取类的全限定名
        String name = joinPoint.getTarget().getClass().getName();

        Set<String> keys = stringRedisTemplate.keys("*");
        for (String ss : keys) {
            //判断key是以name开头的全部删掉
            if (ss.contains(name)) {
                stringRedisTemplate.delete(ss);
            }
        }
    }

}

