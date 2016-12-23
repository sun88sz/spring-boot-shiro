package com.sun.springboot.bean;

import com.sun.springboot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class CommonException extends RuntimeException {

	static Logger log = LoggerFactory.getLogger(CommonException.class);

	private String customMsg;// 报错信息
	private String exceptionCode; // code
	private String exceptionName;
	private Object info;// 报错信息传递的值
	private Map<String, Object> infoMap;// 报错信息传递的值

	public CommonException(String customMsg) {
		super();
		this.customMsg = customMsg;
	}

	public CommonException(Exception exception) {
		this(null, exception);
	}

	public CommonException(String customMsg, Exception e) {
		super();
		this.customMsg = customMsg;
		this.setExceptionName(e.toString());
		this.setExceptionCode(e.getMessage());
		this.setStackTrace(e.getStackTrace());
		if (e instanceof CommonException) {
			this.infoMap = ((CommonException) e).getInfoMap();
		}
	}

	public CommonException(String customMsg, String exceptionCode) {
		this(customMsg);
		this.setExceptionCode(exceptionCode);
	}

	public CommonException(String customMsg, String exceptionCode, Object info) {
		this(customMsg);
		this.exceptionCode = exceptionCode;
		this.info = info;
	}

	public CommonException(String customMsg, String exceptionCode, Map<String, Object> infoMap) {
		this(customMsg);
		this.exceptionCode = exceptionCode;
		this.infoMap = infoMap;
	}

	public String getCustomMsg() {
		return customMsg;
	}

	public void setCustomMsg(String customMsg) {
		this.customMsg = customMsg;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public Map<String, Object> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map<String, Object> infoMap) {
		this.infoMap = infoMap;
	}

	/**
	 * 通配符的替换
	 * 
	 * @param errorMsg
	 * @param param
	 * @return
	 */
	public static String msg(String errorMsg, String param) {
		if (!StringUtils.isEmpty(errorMsg)) {
			errorMsg = errorMsg.replaceFirst("\\{\\}", param);
		}
		return errorMsg;
	}

	public static String msg(String errorMsg, String param1, String param2) {
		if (!StringUtils.isEmpty(errorMsg)) {
			errorMsg = errorMsg.replaceFirst("\\{\\}", param1);
			errorMsg = errorMsg.replaceFirst("\\{\\}", param2);
		}
		return errorMsg;
	}

	public static String msg(String errorMsg, String... params) {
		for (String param : params) {
			errorMsg = errorMsg.replaceFirst("\\{\\}", param);
		}
		return errorMsg;
	}

	public static void main(String[] args) {
		System.out.println(CommonException.msg("sadfsf{}fsd{}", new String[] { "A", "B", "C" }));
	}

}
