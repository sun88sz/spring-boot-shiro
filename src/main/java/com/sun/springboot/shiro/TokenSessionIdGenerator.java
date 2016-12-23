package com.sun.springboot.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

public class TokenSessionIdGenerator implements SessionIdGenerator {

	/**
	 * @param session
	 * @return
	 */
	public Serializable generateId(Session session) {
		return UUID.randomUUID();
	}
}