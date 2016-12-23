package com.sun.springboot.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

public class RedisCache<K, V> implements Cache<K, V> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The wrapped Jedis instance.
	 */
	private StringRedisTemplate cache;

	/**
	 * The Redis key prefix for the sessions
	 */
	private String keyPrefix = "shiro_redis_session:";

	/**
	 * Returns the Redis session keys prefix.
	 * 
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key prefix.
	 * 
	 * @param keyPrefix
	 *            The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	/**
	 * 通过一个JedisManager实例构造RedisCache
	 */
	public RedisCache(StringRedisTemplate cache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache argument cannot be null.");
		}
		this.cache = cache;
	}

	/**
	 * Constructs a cache instance with the specified Redis manager and using a
	 * custom key prefix.
	 * 
	 * @param cache
	 *            The cache manager instance
	 * @param prefix
	 *            The Redis key prefix
	 */
	public RedisCache(StringRedisTemplate cache, String prefix) {
		this(cache);
		// set the prefix
		this.keyPrefix = prefix;
	}

    @Override
    public V get(K key) throws CacheException {
        return null;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        return null;
    }

    @Override
    public V remove(K key) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

//	@Override
//	public V get(K key) throws CacheException {
//		logger.debug("根据key从Redis中获取对象 key [" + key + "]");
//		try {
//			if (key == null) {
//				return null;
//			} else {
//                String v = cache.opsForValue().get(key.toString());
//				V value = (V) SerializeUtils.deserialize(rawValue);
//				return value;
//			}
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//
//	}
//
//	@Override
//	public V put(K key, V value) throws CacheException {
//		logger.debug("根据key从存储 key [" + key + "]");
//		try {
//			cache.set(getByteKey(key), SerializeUtils.serialize(value));
//			return value;
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
//
//	@Override
//	public V remove(K key) throws CacheException {
//		logger.debug("从redis中删除 key [" + key + "]");
//		try {
//			V previous = get(key);
//			cache.del(getByteKey(key));
//			return previous;
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
//
//	@Override
//	public void clear() throws CacheException {
//		logger.debug("从redis中删除所有元素");
//		try {
//			cache.flushDB();
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
//
//	@Override
//	public int size() {
//		try {
//			Long longSize = new Long(cache.dbSize());
//			return longSize.intValue();
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Set<K> keys() {
//		try {
//			Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
//			if (CollectionUtils.isEmpty(keys)) {
//				return Collections.emptySet();
//			} else {
//				Set<K> newKeys = new HashSet<K>();
//				for (byte[] key : keys) {
//					newKeys.add((K) key);
//				}
//				return newKeys;
//			}
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
//
//	@Override
//	public Collection<V> values() {
//		try {
//			Set<byte[]> keys = cache.keys(this.keyPrefix + "*");
//			if (!CollectionUtils.isEmpty(keys)) {
//				List<V> values = new ArrayList<V>(keys.size());
//				for (byte[] key : keys) {
//					@SuppressWarnings("unchecked")
//					V value = get((K) key);
//					if (value != null) {
//						values.add(value);
//					}
//				}
//				return Collections.unmodifiableList(values);
//			} else {
//				return Collections.emptyList();
//			}
//		} catch (Throwable t) {
//			throw new CacheException(t);
//		}
//	}
}