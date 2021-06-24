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

import com.entity.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.util.Constant;
import com.util.ReturnResult;
import com.vo.AjaxResult;

/**
 * 权限验证过滤器
 * @author Administrator
 *
 * @param <T>
 */
@WebFilter(filterName="EValidateAuthority", urlPatterns = { "/limit11111111" })
public class EValidateAuthority<T> implements Filter {
	AjaxResult<T> ajaxResult = new AjaxResult<T>();

    public EValidateAuthority() {
    }
    
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		UserService userService = new UserServiceImpl();
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession(true);
		String method = req.getParameter("method");
		
		if(method!=null&&(method.equals("delUser")
				||method.equals("selectUser")
				||method.equals("searchUser")
				||method.equals("lendLimit")
				)) {
			String key = Constant.IdentityCode+session.getId();
			int user_id = (int) session.getAttribute(key);
			User data = new User();
			data.setUser_id(user_id);
			User user = userService.findOneUser(data);
			if(user.getAuth()!=Constant.AuthAdmin) {  //不是管理员权限
				ajaxResult = ReturnResult.error(Constant.RESCODE_NOAUTH, "权限不足,无法访问");
				ReturnResult.returnResult(ajaxResult, response.getWriter());
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
