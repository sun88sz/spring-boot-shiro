package com.sun.springboot.shiro;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.springboot.consts.SystemConst;
import com.sun.springboot.utils.StringUtils;
import com.sun.springboot.utils.jwt.JWTUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class MySessionManager extends DefaultWebSessionManager {

	public MySessionManager() {
		super();
	}

	/**
	 * 重载getSessionId方法<br/>
	 * 从参数中获取token,解析后获取sessionId
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// 可以从header,request,cookie三种方式获取token
		// 再从token中获取sessionId
		String token = httpRequest.getHeader(SystemConst.HEADER_TOKEN);
		if (StringUtils.isNotEmpty(token)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
		} else {
			token = httpRequest.getParameter(SystemConst.HEADER_TOKEN);
			if (StringUtils.isNotEmpty(token)) {
				request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
						ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
			} else {
				javax.servlet.http.Cookie[] cookies = httpRequest.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (javax.servlet.http.Cookie cookieReq : cookies) {
						if (SystemConst.HEADER_TOKEN.equals(cookieReq.getName())) {
							token = cookieReq.getValue();
						}
					}
				}
				if (StringUtils.isNotEmpty(token)) {
					request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
							ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
				}
			}
		}
		if (StringUtils.isNotBlank(token)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,
					ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
			try {
				DecodedJWT verify = JWTUtils.verify(token);
				Claim claim = verify.getClaim(SystemConst.SESSION_ID);
				String sessionId = claim.asString();

				request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
				request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

				// always set rewrite flag - SHIRO-361
				request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED,
						isSessionIdUrlRewritingEnabled());

				return sessionId;

				//
				// HttpServletResponse rs = (HttpServletResponse) response;
				//
				// Cookie template = getSessionIdCookie();
				// Cookie cookie = new SimpleCookie(template);
				// cookie.setValue(sessionId);
				// cookie.saveTo(httpRequest, rs);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return super.getSessionId(request, response);
	}

	@Override
	protected void onStart(Session session, SessionContext context) {
		super.onStart(session, context);

		if (!WebUtils.isHttp(context)) {
			return;

		}
		HttpServletRequest request = WebUtils.getHttpRequest(context);
		HttpServletResponse response = WebUtils.getHttpResponse(context);

		if (isSessionIdCookieEnabled()) {
			Serializable sessionId = session.getId();
			storeSessionId(sessionId, request, response);
		} else {
		}

		request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
	}

	private void storeSessionId(Serializable currentId, HttpServletRequest request, HttpServletResponse response) {
		if (currentId == null) {
			String msg = "sessionId cannot be null when persisting for subsequent requests.";
			throw new IllegalArgumentException(msg);
		}
		Cookie template = getSessionIdCookie();
		Cookie cookie = new SimpleCookie(template);
		String idString = currentId.toString();
		cookie.setValue(idString);
		cookie.saveTo(request, response);
	}

	// String id = getSessionIdCookieValue(request, response);
	// if (id != null) {
	// request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
	// ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
	// } else {
	// //not in a cookie, or cookie is disabled - try the request URI as a
	// fallback (i.e. due to URL rewriting):
	//
	// //try the URI path segment parameters first:
	// id = getUriPathSegmentParamValue(request,
	// ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
	//
	// if (id == null) {
	// //not a URI path segment parameter, try the query parameters:
	// String name = getSessionIdName();
	// id = request.getParameter(name);
	// if (id == null) {
	// //try lowercase:
	// id = request.getParameter(name.toLowerCase());
	// }
	// }
	// if (id != null) {
	// request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
	// ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
	// }
	// }
	// if (id != null) {
	// request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
	// //automatically mark it valid here. If it is invalid, the
	// //onUnknownSession method below will be invoked and we'll remove the
	// attribute at that time.
	// request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,
	// Boolean.TRUE);
	// }
	//
	// // always set rewrite flag - SHIRO-361
	// request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED,
	// isSessionIdUrlRewritingEnabled());
	//
	// return id;

}