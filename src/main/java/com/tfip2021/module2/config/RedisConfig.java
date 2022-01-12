package com.tfip2021.module2.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@Scope("singleton")
public class RedisConfig {

    @Value("${spring.redis.database}")
    private Integer redisDatabase;
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.password}")
    private String redisPassword;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer redisMaxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer redisMaxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer redisMinIdle;

    @Bean
    public RedisTemplate<String, Object> createRedisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setDatabase(redisDatabase);
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(RedisPassword.of(redisPassword));
        System.out.println(redisPassword);

        final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(redisMaxActive);
        poolConfig.setMinIdle(redisMinIdle);
        poolConfig.setMaxIdle(redisMaxIdle);
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.
            builder().usePooling().
            poolConfig(poolConfig).build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(
            config, jedisClient
        );
        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer(
            getClass().getClassLoader()
        );
        template.setValueSerializer(valueSerializer);
        return template;
    }
}
