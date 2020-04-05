package cn.gwssi.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@PropertySource({"classpath:redis.properties"})
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String type;

    private String host;

    private String clusterNodes;

    private int port;

    private int timeout;

    private int database;

    private String password;

    private int maxRedirects;


    @Autowired
    private GoogleGsonRedisSerializer<Object> googleGsonRedisSerializer;

    @Bean
    public RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate template = new RedisTemplate();

        template.setConnectionFactory(connectionFactory());

        template.setValueSerializer(this.googleGsonRedisSerializer);

        template.setHashValueSerializer(this.googleGsonRedisSerializer);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringRedisSerializer);

        template.setHashKeySerializer(stringRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisConnectionFactory connectionFactory()
    {
        if (StringUtils.isEmpty(this.type)) {
            throw new RuntimeException("未指定spring.redis.type可用参数值：cluster,sentinel,standalone,");
        }
        if ("cluster".equalsIgnoreCase(this.type))
        {
            RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
            redisConfig.setPassword(RedisPassword.of(this.password));
            redisConfig.setMaxRedirects(this.maxRedirects);
            if (StringUtils.isEmpty(this.clusterNodes)) {
                throw new RuntimeException("未配置spring.redis.clusterNodes");
            }
            List redisNodeList = new ArrayList();
            String[] serverArray = this.clusterNodes.split(",");
            for (String server : serverArray) {
                String[] strArr = server.split(":");
                RedisNode redisNode = new RedisNode(strArr[0], Integer.parseInt(strArr[1]));
                redisNodeList.add(redisNode);
            }
            redisConfig.setClusterNodes(redisNodeList);

            return new LettuceConnectionFactory(redisConfig);
        }

        throw new RuntimeException("未找到对应部署类型");
    }
}
