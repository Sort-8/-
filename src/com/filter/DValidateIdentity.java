package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

/**
 * 身份验证过滤器
 * @author Administrator
 *
 * @param <T>
 */
@WebFilter(filterName="DValidateIdentity", urlPatterns = { "/limit" })
public class DValidateIdentity<T> implements Filter {
	AjaxResult<T> ajaxResult = new AjaxResult<T>();
	
    public DValidateIdentity() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		int user_id = 0;
		if(request.getParameter("user_id")!=null) {
			user_id = Integer.valueOf(request.getParameter("user_id"));
			String sessionID = request.getParameter("sessionID");
			String method = request.getParameter("method");
			HttpSession session = req.getSession(true);
			System.out.println("用户身份验证：");
			if(session.getAttribute(sessionID)==null||user_id!=(int)session.getAttribute(sessionID)) {
				session.invalidate();//销毁会话
				ajaxResult = ReturnResult.error(Constant.RESCODE_LOGINEXPIRE, "会话已过期，请重新登录");
				ReturnResult.returnResult(ajaxResult, response.getWriter());
				System.out.println("不通过，销毁当前会话");
				return;
			}else {
				//刷新时间
				session.setMaxInactiveInterval(Constant.MaxImgCodeActiveTime);
				System.out.println("刷新当前时间");
				System.out.println("session key："+ Constant.ValidateCode+session.getId());
				System.out.println("session value："+session.getValue(sessionID));
				System.out.println("session有效期："+session.getMaxInactiveInterval());
				System.out.println("通过");
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
