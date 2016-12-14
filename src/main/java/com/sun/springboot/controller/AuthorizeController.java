package com.sun.springboot.controller;

import com.sun.springboot.bean.User;
import com.sun.springboot.service.ClientService;
import com.sun.springboot.service.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Api(value = "oauth2-authorize-api", description = "oauth2第三方验证登录")
@Controller
public class AuthorizeController {

	@Autowired
	private OAuthService oAuthService;
	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public Object authorizeLogin(HttpServletRequest request, Model model, User user, String client_id,
			String response_type, String redirect_uri)
					throws URISyntaxException, OAuthSystemException, OAuthProblemException {

		// 构建 OAuth 授权请求
		// 检查传入的客户端 id 是否正确
		if (!oAuthService.checkClientId(client_id)) {
			OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("客户端不合法").buildJSONMessage();
			return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
		}

		// 如果用户没有登录,跳转到登陆页面
		Subject subject = SecurityUtils.getSubject();
		// 登录失败时跳转到登陆页面
		if (!login(subject, request)) {
			model.addAttribute("client_id", client_id);
			model.addAttribute("response_type", response_type);
			model.addAttribute("redirect_uri", redirect_uri);
			// return "oauth2login";
			return "login_oauth";
		}
		return createAuthorizationCode(request, subject);

	}

	@ApiOperation(value = "第三方验证")
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public Object authorize(HttpServletRequest request, Model model) throws URISyntaxException, OAuthSystemException {
		try {
			// 构建 OAuth 授权请求
			// 检查传入的客户端 id 是否正确
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("客户端不合法")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 如果用户没有登录,跳转到登陆页面
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				// 登录失败时跳转到登陆页面
				if (!login(subject, request)) {
					model.addAttribute("client_id", oauthRequest.getClientId());
					model.addAttribute("response_type", oauthRequest.getResponseType());
					model.addAttribute("redirect_uri", oauthRequest.getRedirectURI());
					// return "oauth2login";
					return "login_oauth";
				}
			}
			return createAuthorizationCode(request, subject);
		}
		// 出错处理
		catch (OAuthProblemException e) {
			String redirectUri = e.getRedirectUri();
			if (OAuthUtils.isEmpty(redirectUri)) {
				// 告诉客户端没有传入 redirectUri 直接报错
				return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
			}
			// 返回错误消息(如?error=)
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e)
					.location(redirectUri).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		}
	}

	private Object createAuthorizationCode(HttpServletRequest request, Subject subject)
			throws OAuthSystemException, URISyntaxException, OAuthProblemException {

		OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

		String username = (String) subject.getPrincipal();
		// 生成授权码
		String authorizationCode = null;

		// responseType 目前仅支持 CODE,另外还有 TOKEN
		String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
		if (responseType.equals(ResponseType.CODE.toString())) {
			OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			authorizationCode = oauthIssuerImpl.authorizationCode();
			oAuthService.addAuthCode(authorizationCode, username);
		}

		// 进行 OAuth 响应构建
		OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
				HttpServletResponse.SC_FOUND);
		// 设置授权码
		builder.setCode(authorizationCode);

		// 得到到客户端重定向地址
		String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
		// 构建响应
		final OAuthResponse response = builder.location(redirectURI).buildQueryMessage(); // 根据
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(response.getLocationUri()));
		return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
	}

	/**
	 * @param subject
	 * @param request
	 * @return
	 */
	private boolean login(Subject subject, HttpServletRequest request) {
//		if ("get".equalsIgnoreCase(request.getMethod())) {
//			return false;
//		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
		// return false;
		// }
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}
}