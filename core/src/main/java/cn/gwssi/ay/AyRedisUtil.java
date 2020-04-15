package cn.gwssi.ay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("ayRedisUtil")
public final class AyRedisUtil
{

    @Autowired
    private StringRedisTemplate redisAyTemplate;
    //private RedisTemplate<String, Object> redisTemplate;

    public Object getall(String key){

        return this.redisAyTemplate.opsForHash().entries(key);

    }

    public Long  zrankaj(String key,String value){

        return this.redisAyTemplate.opsForZSet().rank(key, value);

    }
}
