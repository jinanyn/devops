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
    public Object getall(String key){

        return this.redisAyTemplate.opsForHash().entries(key);
    }
    public Long  zrankaj(String key,String value){
        return this.redisAyTemplate.opsForZSet().rank(key, value);

    }

    public void setScytasx (String key,String value){
        this.redisAyTemplate.opsForValue().set("SYS_CONFIG_SCYDCTASX_"+key, value);
    }
}
