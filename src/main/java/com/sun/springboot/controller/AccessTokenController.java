package com.sun.springboot.controller;

import com.sun.springboot.bean.User;
import com.sun.springboot.service.OAuthService;
import com.sun.springboot.utils.jwt.UserTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.net.URISyntaxException;

@Api(value = "oauth2-accessToken-api", description = "oauth2第三方获取accessToken")
@RestController
public class AccessTokenController {
	@Autowired
	private OAuthService oAuthService;

	@ApiOperation(value = "获取accessToken")
	@RequestMapping("/accessToken")
	public HttpEntity token(HttpServletRequest request, HttpServletResponse response)
			throws URISyntaxException, OAuthSystemException {
		try {
			// 构建 OAuth 请求
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
			// 检查提交的客户端 id 是否正确
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("xxxx")
						.buildJSONMessage();
				return new ResponseEntity(oauthResponse.getBody(),
						HttpStatus.valueOf(oauthResponse.getResponseStatus()));
			}
			// 检查客户端安全 KEY 是否正确
			if (!oAuthService.checkClientSecret(oauthRequest.getClientId(), oauthRequest.getClientSecret())) {
				OAuthResponse oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).setErrorDescription("xxxx")
						.buildJSONMessage();
				return new ResponseEntity(oauthResponse.getBody(),
						HttpStatus.valueOf(oauthResponse.getResponseStatus()));
			}
			String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
			// 检查验证类型,此处只检查 AUTHORIZATION_CODE 类型,其他的还有 PASSWORD REFRESH_TOKEN
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
				if (!oAuthService.checkAuthCode(authCode)) {
					OAuthResponse oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription("错误的授权码")
							.buildJSONMessage();
					return new ResponseEntity(oauthResponse.getBody(),
							HttpStatus.valueOf(oauthResponse.getResponseStatus()));
				}
			}
			// 生成 Access Token
			// OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new
			// MD5Generator());
			// final String accessToken = oauthIssuerImpl.accessToken();

			String usernameByAuthCode = oAuthService.getUsernameByAuthCode(authCode);
			// JWT生成的token

			Serializable sessionId = SecurityUtils.getSubject().getSession().getId();
			String accessToken = UserTokenUtils.createNewToken(request, response, new User(), sessionId.toString());

			oAuthService.addAccessToken(accessToken, oAuthService.getUsernameByAuthCode(authCode));
			// 生成 OAuth 响应
			OAuthResponse oauthResponse = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken).setExpiresIn(oAuthService.getExpireIn()).buildJSONMessage();
			// 根据 OAuthResponse 生成 ResponseEntity
			return new ResponseEntity(oauthResponse.getBody(), HttpStatus.valueOf(oauthResponse.getResponseStatus()));
		} catch (OAuthProblemException e) {
			// 构建错误响应
			OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			return new ResponseEntity(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
		}
	}
}