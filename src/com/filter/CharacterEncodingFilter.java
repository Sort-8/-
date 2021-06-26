package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.util.Constant;

/**
 * 全局编码过滤器
 * @author Administrator
 *
 */
@WebFilter(filterName="CharacterEncodingFilter", urlPatterns = { 
		"/user","/book","/record","/limit","/type","/statistics" 
		})
public class CharacterEncodingFilter implements Filter {
    public CharacterEncodingFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(Constant.REQUESTCODE);		//设置请求编码
		response.setContentType(Constant.JSONREPONSECODE);      //设置响应编码
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}
}
