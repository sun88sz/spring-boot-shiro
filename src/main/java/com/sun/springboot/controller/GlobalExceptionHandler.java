package com.sun.springboot.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ UnauthenticatedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String unauthenticatedException(HttpServletRequest request, HttpServletResponse response,
			UnauthenticatedException e) {
		return "../static/401";
	}

	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String processUnauthenticatedException(HttpServletRequest request, HttpServletResponse response,
			UnauthorizedException e) {
		return "../static/403";
	}

	@ExceptionHandler
	public String handlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {
		log.error("GlobalExceptionHandler 捕获异常 并将错误返回前台 ", e);
		throw e;
	}

}
