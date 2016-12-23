package com.sun.springboot.utils.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.springboot.bean.CommonException;
import com.sun.springboot.bean.User;
import com.sun.springboot.consts.SystemConst;
import com.sun.springboot.service.IUserService;
import com.sun.springboot.utils.JsonUtils;
import com.sun.springboot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class UserTokenUtils {

	private static Logger log = LoggerFactory.getLogger(UserTokenUtils.class);

	@Autowired
	private static StringRedisTemplate redisTemplate;

	private static IUserService userBiz;

	/**
	 * 登录,token,refresh 三个方法设置token
	 *
	 * @param request
	 * @param response
	 * @param token
	 */
	public static void addCookieToken(HttpServletRequest request, HttpServletResponse response, String token) {
		// 设置cookie方式
		Cookie cookie = new Cookie(SystemConst.HEADER_TOKEN, token);
		response.addCookie(cookie);
		// 设置header方式
		response.setHeader(SystemConst.HEADER_TOKEN, token);
	}

	/**
	 * token登录方式 <br>
	 * 从redis里获取用户信息
	 *
	 * @param request
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest request) {

		// 验证token是否有效,并获取userId
		String tokenValue = getTokenValue(request);
		if (!StringUtils.isEmpty(tokenValue)) {
			// 解析token获取userId
			Long userId = getUserIdByToken(tokenValue);
			if (userId != null) {
				String accessWay = getAccessWay(request);

				// 先从redis读取用户缓存
				User user = null;
				String userStr = redisTemplate.opsForValue().get(SystemConst.REDIS_KEY_USER_CACHE + ":" + userId);
				if (!StringUtils.isEmpty(userStr)) {
					user = JsonUtils.jsonToObj(userStr, User.class);
				}

				// 如果缓存读不到user,则从数据库中查询,再缓存到redis
				if (user == null) {
					user = userBiz.selectByIdApproveLogin(userId);
					if (user != null) {
						userStr = JsonUtils.objToJson(user);
						redisTemplate.opsForValue().set(SystemConst.REDIS_KEY_USER_CACHE + ":" + userId, userStr,
								SystemConst.TOKEN_EXPIRE_TIME_USER_CACHE);
					}
				}
				if (user != null) {
					Long employeeId = getEmployeeIdByToken(tokenValue);
					if (employeeId != null) {
						String employeeStr = redisTemplate.opsForValue()
								.get(SystemConst.REDIS_KEY_EMPLOYEE_CACHE + ":" + employeeId);
						// if (!StringUtils.isEmpty(employeeStr)) {
						// EmployeeModel em = JsonUtils.jsonToObj(employeeStr,
						// EmployeeModel.class);
						// user.setCurrentEmployee(em);
						// }
					}
					user.setAccessWay(accessWay);
					return user;
				}
			}
		}
		throw new CommonException("用户登录超时，页面已过期，请重新登录", "401");
	}

	/**
	 * 获取访问方式 WEB/APP/WECHAT
	 *
	 * @param request
	 * @return
	 */
	public static String getAccessWay(HttpServletRequest request) {
		String tokenValue = request.getHeader(SystemConst.HEADER_ACCESS);
		if (StringUtils.isEmpty(tokenValue)) {
			tokenValue = request.getParameter(SystemConst.HEADER_ACCESS);
		}
		if (StringUtils.isEmpty(tokenValue)) {
			tokenValue = "WEB";
		}
		return tokenValue;
	}

	/**
	 * @param tokenValue
	 * @return
	 */
	public static Long getUserIdByToken(String tokenValue) {
		// 验证token是否有效
		DecodedJWT djwt = validateUserToken(tokenValue);
		Claim claim = djwt.getClaim(SystemConst.TOKEN_USER_ID);
		if (claim != null && !claim.isNull()) {
			return Long.valueOf(claim.toString());
		}
		return null;
	}

	/**
	 * @param tokenValue
	 * @return
	 */
	private static Long getEmployeeIdByToken(String tokenValue) {
		// 验证token是否有效
		DecodedJWT djwt = validateUserToken(tokenValue);
		Claim claim = djwt.getClaim(SystemConst.TOKEN_EMPLOYEE_ID);
		if (claim != null && !claim.isNull()) {
			return Long.valueOf(claim.toString());
		}
		return null;
	}

	/**
	 * 获取token值
	 *
	 * @param request
	 * @return
	 */
	public static String getTokenValue(HttpServletRequest request) {
		String tokenValue = request.getHeader(SystemConst.HEADER_TOKEN);
		if (StringUtils.isEmpty(tokenValue)) {
			tokenValue = request.getParameter(SystemConst.HEADER_TOKEN);
		}
		return tokenValue;
	}

	/**
	 * @param tokenValue
	 * @return
	 * @throws CommonException
	 */
	private static DecodedJWT validateUserToken(String tokenValue) {
		DecodedJWT verify;
		try {
			verify = JWTUtils.verify(tokenValue);
		} catch (Exception e) {
			throw new CommonException("用户登录超时，页面已过期，请重新登录", "401");
		}
		return verify;
	}

	private static Object getClaim(String tokenValue, String key) {
		DecodedJWT decodedJWT = validateUserToken(tokenValue);
		return decodedJWT.getClaim(key);
	}

	/**
	 * 登录 和 刷新缓存的时候调用 创建一个新的token
	 *
	 * @param request
	 * @param response
	 */
	public static String createNewToken(HttpServletRequest request, HttpServletResponse response) {
		return createNewToken(request, response, null, null, false);
	}

	/**
	 * 登录
	 *
	 * @param request
	 * @param response
	 * @param currentUser
	 */
	public static String createNewToken(HttpServletRequest request, HttpServletResponse response, User currentUser,
			String sessionId) {
		return createNewToken(request, response, currentUser, sessionId, false);
	}

	/**
	 * @param request
	 * @param response
	 * @param currentUser
	 */
	public static String createNewToken(HttpServletRequest request, HttpServletResponse response, User currentUser,
			String sessionId, boolean delImmediately) {
		checkAppVersion(request);

		String access = getAccessWay(request);
		String oldToken = getTokenValue(request);

		Long userId = null;
		Long employeeId = null;
		// 如果currentUser!=null是登录产生新token,保存user到redis
		if (currentUser != null) {
			// if (currentUser.getCurrentEmployee() != null) {
			// EmployeeModel currentEmployee = currentUser.getCurrentEmployee();
			// employeeId = currentEmployee.getEmployeeId();
			// String currentEmployeeStr = JsonUtils.objToJson(currentEmployee);
			// baseRedisDAO.setEx(SystemConst.REDIS_KEY_EMPLOYEE_CACHE + ":" +
			// currentEmployee.getEmployeeId(),
			// currentEmployeeStr, SystemConst.TOKEN_EXPIRE_TIME_USER_CACHE *
			// 4);
			// currentUser.setCurrentEmployee(null);
			// }

			userId = currentUser.getUserId();
			String currentUserStr = JsonUtils.objToJson(currentUser);
			redisTemplate.opsForValue().set(SystemConst.REDIS_KEY_USER_CACHE + ":" + currentUser.getUserId(),
					currentUserStr, SystemConst.TOKEN_EXPIRE_TIME_USER_CACHE, TimeUnit.SECONDS);
		}
		// 如果currentUser==null是刷新产生新token,旧token应该有userId
		else {
			userId = getUserIdByToken(oldToken);
			employeeId = getEmployeeIdByToken(oldToken);
		}

		// 设置过期时间
		Long expireSecs;
		if ("APP".equals(access)) {
			expireSecs = SystemConst.TOKEN_EXPIRE_TIME_APP;
		} else if ("WECHAT".equals(access)) {
			expireSecs = SystemConst.TOKEN_EXPIRE_TIME_WECHAT;
		} else {
			expireSecs = SystemConst.TOKEN_EXPIRE_TIME_WEB;
		}

		Map<String, Object> params = new HashMap<>();
		if (sessionId != null) {
			params.put(SystemConst.SESSION_ID, sessionId);
		}
		params.put(SystemConst.TOKEN_USER_ID, userId);
		if (employeeId != null) {
			params.put(SystemConst.TOKEN_EMPLOYEE_ID, employeeId);
		}
		String newToken = JWTUtils.createToken(expireSecs, params);

		// 删除旧的token
		if (delImmediately) {
			redisTemplate.delete(SystemConst.REDIS_KEY_USER_TOKEN + ":" + oldToken);
		} else {
			redisTemplate.expire(SystemConst.REDIS_KEY_USER_TOKEN + ":" + oldToken, SystemConst.TOKEN_EXPIRE_DELETE,
					TimeUnit.SECONDS);
		}
		// 添加新的token
		redisTemplate.opsForValue().set(SystemConst.REDIS_KEY_USER_TOKEN + ":" + newToken, "", expireSecs);
		response.setHeader(SystemConst.HEADER_TOKEN, newToken);

		return newToken;
	}

	/**
	 * 使token失效
	 *
	 * @param request
	 */
	public static void disableToken(HttpServletRequest request) {
		try {
			User currentUser = getCurrentUser(request);
			String oldToken = request.getHeader(SystemConst.HEADER_TOKEN);
			String oldKey = SystemConst.REDIS_KEY_USER_TOKEN + ":" + currentUser.getUserId() + ":" + oldToken;
			redisTemplate.expire(oldKey, SystemConst.TOKEN_EXPIRE_DELETE, TimeUnit.SECONDS);
		} catch (Exception e) {

		}
	}

	/**
	 * @param request
	 * @return
	 */
	public static void deleteSynchronizeToken(HttpServletRequest request) {
		try {
			String tokenValue = getTokenValue(request);
			redisTemplate.delete(SystemConst.REDIS_KEY_USER_TOKEN + ":" + tokenValue);
		} catch (Exception e) {

		}
	}

	/**
	 * 是否存在该token(如已在redis中被删除,token就算本身验证通过也是无效的)
	 *
	 * @param request
	 * @return
	 */
	public static boolean existSynchronizeToken(HttpServletRequest request) {
		try {
			String tokenValue = getTokenValue(request);
			Set<String> keys = redisTemplate.keys(SystemConst.REDIS_KEY_USER_TOKEN + ":" + tokenValue);
			if (keys != null && keys.size() > 0) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 检测app版本是否是最新版
	 *
	 * @return
	 */
	public static boolean checkAppVersion(HttpServletRequest request) {
		String access = request.getHeader(SystemConst.HEADER_ACCESS);
		if ("APP".equals(access)) {
			String userAgent = request.getHeader("User-Agent");
			String version = request.getHeader(SystemConst.APP_HEADER_VERSION);
			// 是否开启app版本过滤
			if (SystemConst.APP_VERSION_FILTER_OPEN_ANDROID && userAgent.toLowerCase().indexOf("android") >= 0) {
				if (SystemConst.APP_CURRENT_VERSION_ANDROID.compareTo(version) > 0) {
					throw new CommonException(SystemConst.APP_VERSION_ERROR_MSG);
				}
			} else if (SystemConst.APP_VERSION_FILTER_OPEN_IOS && userAgent.toLowerCase().indexOf("iphone") >= 0) {
				if (SystemConst.APP_CURRENT_VERSION_IOS.compareTo(version) > 0) {
					throw new CommonException(SystemConst.APP_VERSION_ERROR_MSG);
				}
			}
		}
		return true;
	}

}