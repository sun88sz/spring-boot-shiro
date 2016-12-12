package com.sun.springboot.service;

import org.springframework.stereotype.Service;

/**
 * Created by Sun on 16/12/9.
 */
@Service
public class OAuthServiceImpl implements OAuthService {
	@Override
	public void addAuthCode(String authCode, String username) {

	}

	@Override
	public void addAccessToken(String accessToken, String username) {

	}

	@Override
	public boolean checkAccessToken(String accessToken) {
		return false;
	}

	@Override
	public String getUsernameByAuthCode(String authCode) {
		return null;
	}

	@Override
	public String getUsernameByAccessToken(String accessToken) {
		return null;
	}

	@Override
	public boolean checkClientId(String clientId) {
		return true;
	}

	@Override
	public boolean checkClientSecret(String clientId, String clientSecret) {
		if ("qaz".equals(clientId) && "wsxedcrfv".equals(clientSecret)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAuthCode(String authCode) {
		return true;
	}

	@Override
	public String getExpireIn() {
		return "1000";
	}
}
