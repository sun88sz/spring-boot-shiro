package com.sun.configuration.redis;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(MyRedisProperties.class)
public class MyRedisConfiguration {

	@Bean(name = "stringRedisTemplate2")
	public StringRedisTemplate hotelRedisTemplate(MyRedisProperties properties) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory(properties));
		return redisTemplate;
	}

	@Bean(name = "redisTemplate2")
	public RedisTemplate redisTemplate(MyRedisProperties properties) {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory(properties));
		return redisTemplate;
	}

	private RedisConnectionFactory jedisConnectionFactory(MyRedisProperties properties) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(properties.getPool().getMaxActive());
		poolConfig.setMaxIdle(properties.getPool().getMaxIdle());
		poolConfig.setMaxWaitMillis(properties.getPool().getMaxWait());
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnCreate(true);
		poolConfig.setTestWhileIdle(true);

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
		jedisConnectionFactory.setHostName(properties.getHost());
		if (null != properties.getPassword()) {
			jedisConnectionFactory.setPassword(properties.getPassword());
		}
		jedisConnectionFactory.setPort(properties.getPort());

		// 其他配置，可再次扩展

		return jedisConnectionFactory;
	}
}
