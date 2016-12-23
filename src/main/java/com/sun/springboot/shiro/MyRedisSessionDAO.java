package com.sun.springboot.shiro;

import com.sun.springboot.consts.SystemConst;
import com.sun.springboot.utils.JsonUtils;
import com.sun.springboot.utils.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sun on 16/12/16.
 */
public class MyRedisSessionDAO extends EnterpriseCacheSessionDAO {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = getSessionIdGenerator().generateId(session);
		assignSessionId(session, sessionId);
		String value = JsonUtils.objToJson(session);

		redisTemplate.opsForValue().set(SystemConst.REDIS_KEY_USER_TOKEN + sessionId.toString(), value, 1000,TimeUnit.SECONDS);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		String value = redisTemplate.opsForValue().get(SystemConst.REDIS_KEY_USER_TOKEN + sessionId.toString());
		Session session = JsonUtils.jsonToObj(value, Session.class);
		return session;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {

	}

	@Override
	public void delete(Session session) {
		Serializable sessionId = session.getId();
		redisTemplate.expire(SystemConst.REDIS_KEY_USER_TOKEN + sessionId.toString(), 120, TimeUnit.SECONDS);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<String> keys = redisTemplate.keys(SystemConst.REDIS_KEY_USER_TOKEN + "*");
		List<String> sessionStrs = redisTemplate.opsForValue().multiGet(keys);

		if (sessionStrs != null && sessionStrs.size() > 0) {
			List<Session> sessions = new ArrayList<>();
			for (String sessionStr : sessionStrs) {
				if (StringUtils.isNotBlank(sessionStr)) {
					Session session = JsonUtils.jsonToObj(sessionStr, Session.class);
					sessions.add(session);
				}
			}
			return sessions;
		}
		return null;
	}
}
