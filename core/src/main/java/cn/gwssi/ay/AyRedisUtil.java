package cn.gwssi.ay;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public final class AyRedisUtil
{

    @Resource(name = "redisAyTemplate")
    private StringRedisTemplate redisTemplate;
    //private RedisTemplate<String, Object> redisTemplate;

    public Object getall(String key){

        return this.redisTemplate.opsForHash().entries(key);

    }

    public Long  zrankaj(String key,String value){

        return this.redisTemplate.opsForZSet().rank(key, value);

    }
}
