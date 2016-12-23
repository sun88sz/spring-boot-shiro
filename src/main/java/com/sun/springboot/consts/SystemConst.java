package com.sun.springboot.consts;

public class SystemConst {

	/******************************************************************************************************
	 ****************************************************************************************************** 
	 ****************************************************************************************************** 
	 * 系统相关
	 */
	public static String CURRENT_EMPLOYEE = "currentEmployee";
	public static String CURRENT_USER = "currentUser";

	public static String SESSION_ID = "sessionId";
	public static String HEADER_TOKEN = "token";
	public static String HEADER_REQUEST_ID = "request_id";
	public static String HEADER_ACCESS = "access";

	public static String TOKEN_ENCRYPT_KEY = "LUOPAN66611666";// token加密字串
	public static String TOKEN_ISSUE_KEY = "luopanissue";

	public static String TOKEN_USER_ID = "userId";
	public static String TOKEN_EMPLOYEE_ID = "employeeId";
	public static String TOKEN_CHANNEL_ID = "channelId";
	public static Long TOKEN_EXPIRE_TIME_USER_CACHE = 60 * 30l;// 用户缓存有效期
	public static Long TOKEN_EXPIRE_TIME_APP = 60 * 60 * 24 * 7l;// 移动端token有效期
	public static Long TOKEN_EXPIRE_TIME_WECHAT = 60 * 60 * 12l;// 微信公众号token有效期
	public static Long TOKEN_EXPIRE_TIME_WEB = 60 * 60 * 2l;// web端token有效期
	public static Long TOKEN_EXPIRE_DELETE = 120l;// 旧token待删除（用于刷新后token后还是用原始token的缓冲时间）

	/******************************************************************************************************
	 ****************************************************************************************************** 
	 ****************************************************************************************************** 
	 * redis 缓存key名
	 */
	public static String REDIS_KEY_USER_TOKEN = "SHIRO_USER_TOKEN";
	public static String REDIS_KEY_USER_CACHE = "USER_CACHE";
	public static String REDIS_KEY_EMPLOYEE_TOKEN = "EMPLOYEE_TOKEN";
	public static String REDIS_KEY_EMPLOYEE_CACHE = "EMPLOYEE_CACHE";

	/******************************************************************************************************
	 ****************************************************************************************************** 
	 ****************************************************************************************************** 
	 * app相关
	 */
	public static String APP_CURRENT_VERSION_IOS = "1.0.1";
	public static String APP_CURRENT_VERSION_ANDROID = "1.0.1";
	public static String APP_HEADER_VERSION = "version";

	public static Boolean APP_VERSION_FILTER_OPEN_IOS = false;
	public static Boolean APP_VERSION_FILTER_OPEN_ANDROID = false;

	public static String APP_VERSION_ERROR_MSG = "系统维护中...";

	public static String APP_LINK_IOS;
	public static String APP_LINK_ANDROID = "http://www.jiketravel.com/compass.apk";

	public static String AUDIT_VISION_AUDIT_ANDRIOD = "old"; // 审核旧模块开关 安卓
	public static String AUDIT_VISION_AUDIT_IOS = "old"; // 审核旧模块开关 IOS

}
