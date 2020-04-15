package cn.gwssi.redis;

import com.google.gson.Gson;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component("xxGoogleGsonRedisSerializer")
public class GoogleGsonRedisSerializer<T>
        implements RedisSerializer<T>
{
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        Gson gson = new Gson();
        return gson.toJson(t).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes)
            throws SerializationException
    {
        if ((bytes == null) || (bytes.length <= 0)) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) str;
    }
}