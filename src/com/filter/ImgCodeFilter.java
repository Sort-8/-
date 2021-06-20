package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.util.Constant;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;

/**
 * 验证码图片请求过滤器
 * @author Administrator
 *
 */
@WebFilter(filterName="ImgCodeFilter", urlPatterns = { "/getcode" })
public class ImgCodeFilter implements Filter {

    public ImgCodeFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(Constant.ImgHeight,  //图片高度
															  Constant.Imgwidth,   //图片宽带
															  Constant.CodeCount,  //验证码字符数
															  Constant.LineWidth); //干扰线宽度
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession(true);
		String key = Constant.ValidateCode + session.getId();
		session.setAttribute(key,captcha.getCode());
		session.setMaxInactiveInterval(Constant.MaxImgCodeActiveTime);
		
		System.out.println("session值："+session.getValue(key));
		System.out.println("session key："+ Constant.ValidateCode+session.getId());
		System.out.println("session有效期："+session.getMaxInactiveInterval());
		
		ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);
        outputStream.close();
        return ;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
