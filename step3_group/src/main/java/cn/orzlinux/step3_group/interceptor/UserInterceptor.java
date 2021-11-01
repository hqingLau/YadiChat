package cn.orzlinux.step3_group.interceptor;

import cn.orzlinux.step3_group.Utils.CookieUtils;
import cn.orzlinux.step3_group.server.UserSession;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor  {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if("/".equals(uri)||"/error".equals(uri)||"/signin".equals(uri)||"/login".equals(uri)||"/signup".equals(uri)||"/regist".equals(uri)) {
            return true;
        }
        Cookie cookie = CookieUtils.getCookie(request.getCookies(),"user");
        if(cookie!=null) {
            UserSession.update(cookie.getValue());
            return true;
        }
        response.sendRedirect("/signin");
        return false;

        //return true;
    }
}
