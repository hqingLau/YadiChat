package cn.orzlinux.step1_signup_in.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if("/".equals(uri)||"/error".equals(uri)||"/signin".equals(uri)||"/login".equals(uri)||"/signup".equals(uri)||"/regist".equals(uri)) {
            return true;
        }
        Object o = request.getSession().getAttribute("userid");
        if(null == o) {
            response.sendRedirect("/signin");
            return false;
        }
        return true;
    }
}
