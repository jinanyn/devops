package cn.gwssi.ay;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@EnableCaching
@Configuration
public class RedisDevConfiguration {

    @Bean(name = "redisAyTemplate")
    public StringRedisTemplate redisUatTemplate(@Value("${spring.redis1.host}") String hostName,
                                                @Value("${spring.redis1.port}") int port, @Value("${spring.redis1.password}") String password,
                                                @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle, @Value("${spring.redis.lettuce.pool.max-active}") int maxTotal,
                                                @Value("${spring.redis1.database}") int index, @Value("${spring.redis.lettuce.pool.max-wait}") long maxWaitMillis, @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle) {
        StringRedisTemplate temple = new StringRedisTemplate();
        temple.setConnectionFactory(
                connectionFactory(hostName, port, password, maxIdle, maxTotal, index, maxWaitMillis,minIdle));

        return temple;
    }
    public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int maxIdle,
                                                    int maxTotal, int index, long maxWaitMillis, int minIdle) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (StringUtils.isNotEmpty(password)) {
            jedis.setPassword(password);
        }
        if (index != 0) {
            jedis.setDatabase(index);
        }
        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis,minIdle));
        // 初始化连接pool
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;

        return factory;
    }
    public JedisPoolConfig poolCofig(int maxIdle, int maxTotal, long maxWaitMillis, int minIdle) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMaxTotal(maxTotal);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setMinIdle(minIdle);
        return poolCofig;
    }
}
