package com.sun.springboot.controller;

import com.sun.springboot.bean.User;
import com.sun.springboot.utils.jwt.JWTUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;

/**
 * Shiro登录Controller
 *
 */
@Api(value = "login-api", description = "用户登录")
@Controller
public class ShiroController {

	private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

	@ApiOperation(value = "默认路径", notes = "默认路径返回登录页面")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "login";
	}

	@ApiOperation(value = "登录页面", notes = "获取登录页面")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@ApiOperation(value = "登录", notes = "提交登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User user, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "login";
		}

		String username = user.getUsername();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();


		String jwt = JWTUtils.createToken(1000l, null);

		Session session = currentUser.getSession();
		session.setAttribute("jwt",jwt);





		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		} catch (LockedAccountException lae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {




			logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			return "redirect:/hello";
		} else {
			token.clear();
			return "redirect:/login";
		}
	}

	@ApiOperation(value = "登出", notes = "退出登录接口")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes) {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/login";
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}

	@RequestMapping(value = "/401", method = RequestMethod.GET)
	@ResponseBody
	public String unauthorizedRole(HttpServletRequest request, HttpServletResponse response) {
		logger.info("------没有权限-------");


		Serializable id = SecurityUtils.getSubject().getSession().getId();
		System.out.println(id);

//		Cookie[] cookies = request.getCookies();
//		for (int i = 0; i < cookies.length; i++) {
//			String name = cookies[i].getName();
//			String value = cookies[i].getValue();
//
//			System.out.println(name + value);
//		}

//		String token = JWTUtils.createToken(1000l, null);
//
//		Cookie cookie = new Cookie("Token", token);
//		cookie.setHttpOnly(true);
//		cookie.setMaxAge(100);
//		// cookie.setDomain(".l.com");//包含子域名
//		response.addCookie(cookie);

		return "401";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	@RequiresPermissions("xxx")
	public String noPermission() {
		return "../static/403";
	}

	// @RequestMapping("/user")
	// public String getUserList(Map<String, Object> model){
	// model.put("userList", userDao.getList());
	// return "user";
	// }

	@RequestMapping(value = "/user/edit/{userid}", method = RequestMethod.GET)
	@RequiresPermissions("xxx")
	public String getUserList(@PathVariable int userid) {
		logger.info("------进入用户信息修改-------");
		return "user_edit";
	}
}