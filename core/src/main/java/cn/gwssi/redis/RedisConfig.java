package cn.gwssi.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@PropertySource({"classpath:redis.properties"})
//@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    @Value("${spring.redis.type}")
    private String type;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.clusterNodes}")
    private String clusterNodes;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.maxRedirects}")
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
            //redisConfig.setMaxRedirects(this.maxRedirects);
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
        }else if("standalone".equalsIgnoreCase(this.type)){
            RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
            redisConfig.setHostName(this.host);
            redisConfig.setPassword(this.password);
            redisConfig.setPort(this.port);
            redisConfig.setDatabase(this.database);

            return new LettuceConnectionFactory(redisConfig);
        }

        throw new RuntimeException("未找到对应部署类型");
    }
}
