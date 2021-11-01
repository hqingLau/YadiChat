package cn.orzlinux.step3_group.controller;

import cn.orzlinux.step3_group.bean.User;
import cn.orzlinux.step3_group.server.UserSession;
import cn.orzlinux.step3_group.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    // 仅仅返回登录界面
    @RequestMapping("/signin")
    public String signin() {
        return "sign-in";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @RequestMapping("/room")
    public String room() {
        return "room";
    }

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        String idname = request.getParameter("idname");
        String password = request.getParameter("password");
        User user = userService.login(new User(idname,null,password));
        if (user==null) {
            return "redirect:/signin";
        }
        // 随便造一个session，放入
        //request.getSession().setAttribute("userid", idname);
        String userUUID = idname;//UUID.randomUUID().toString().replaceAll("-","");
        UserSession.put(userUUID,user);
        Cookie cookie = new Cookie("user",userUUID);
        cookie.setMaxAge(24*60*60); //一天过期
        // 配置cookie为主站cookie
        //cookie.setDomain("orzlinux.cn");
        response.addCookie(cookie);
        return "redirect:/home";
    }

    // 注册
    @GetMapping("/signup")
    public String signup() {
        return "sign-up";
    }

    @PostMapping("/regist")
    public String regist(Model model, HttpServletRequest request, HttpServletResponse response) {
        String idname = request.getParameter("idname");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        try {
            User user = new User(idname,nickname,password);
            boolean ret = userService.regist(user);
            if (!ret) {
                return "redirect:/signup";
            }
            String userUUID = idname;//UUID.randomUUID().toString().replaceAll("-","");
            UserSession.put(userUUID,user);
            Cookie cookie = new Cookie("user",userUUID);
            cookie.setMaxAge(24*60*60); //一天过期
            // 配置cookie为主站cookie
            //cookie.setDomain("orzlinux.cn");
            response.addCookie(cookie);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/signup";
        }

    }

    @RequestMapping("/somepage")
    @ResponseBody
    public String somepage(HttpServletRequest request, HttpServletResponse response) {
        return "private msg";
    }
}
