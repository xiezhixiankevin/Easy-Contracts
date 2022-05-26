package bjtu.pt.easycontracts.interceptor;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <Description> LoginInterceptor 登录拦截器，防止不登陆访问后台
 *
 * @author 26802
 * @version 1.0
 * @ClassName LoginInterceptor
 * @taskId
 * @see bjtu.pt.easycontracts.interceptor
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getSession().getAttribute("nowUser")==null){
            response.sendRedirect("/toLogin");
            return false;
        }
        return true;
    }
}
