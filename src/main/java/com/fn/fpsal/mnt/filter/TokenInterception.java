package com.fn.fpsal.mnt.filter;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fn.fpsal.mnt.utils.TokenUtils;

public class TokenInterception extends HandlerInterceptorAdapter {

	private static String TOKEN = "token";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*获取用户凭证*/
		String token = request.getHeader(TOKEN);
		if(StringUtils.isEmpty(token)) {
			token = request.getParameter(TOKEN);
		}
		if(StringUtils.isEmpty(token)) {
			Object tokenObj = request.getAttribute(TOKEN);
			if(tokenObj!=null) {
				token = tokenObj.toString();
			}
		}
		/*token凭证为空*/
		if(StringUtils.isEmpty(token)) {
			response.setStatus(403);
			throw new AuthException(TOKEN + "不能为空");
		}
		/*校验token有效性*/
		if(!TokenUtils.volidateToken(token)) {
			response.setStatus(403);
			throw new AuthException(TOKEN + "无效");
		}
		return true;
	}
}
