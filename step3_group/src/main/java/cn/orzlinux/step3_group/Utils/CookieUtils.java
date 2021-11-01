package cn.orzlinux.step3_group.Utils;

import cn.orzlinux.step3_group.server.UserSession;

import javax.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie getCookie(javax.servlet.http.Cookie[] cookies, String key) {
        if(cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    if (UserSession.get(cookie.getValue()) != null) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }
}
