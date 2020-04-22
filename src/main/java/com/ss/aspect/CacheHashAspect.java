package com.ss.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
@Aspect
public class CacheHashAspect {

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    //@Around("@annotation(com.ss.annotation.AddCache)")
    public Object addCahe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("环绕通知");

        //序列化解决乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        StringBuilder sb = new StringBuilder();

        //key,value   类型
        //key 类的全限定名+方法名+参数名（实参）
        //value :缓存的数据  String

        //KEY  Hash<Key,value>
        // 类全限定名   (方法名+参数名（实参）,数据)
        //    1方法   数据
        //    2方法   数据

        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);

        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        //拼接key   小key
        String key = sb.toString();

        //取出key
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(className, key);

        HashOperations hashOperations = redisTemplate.opsForHash();

        Object result =null;

        //去Redis判断key是否存在
        if(aBoolean){
            //存在  缓存中有数据  取出数据返回结果
            result = hashOperations.get(className,key);
        }else{
            //不存在   缓存中没有   放行方法得到结果
            result = proceedingJoinPoint.proceed();

            //拿到返回结果  加入缓存
            hashOperations.put(className,key,result);
        }
        return result;
    }

    //@After("@annotation(com.ss.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){

        //清空缓存
        //获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //删除该类下所有的数据
        redisTemplate.delete(className);
    }
}
