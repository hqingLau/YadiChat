package cn.orzlinux.step1_signup_in.controller;

import cn.orzlinux.step1_signup_in.bean.User;
import cn.orzlinux.step1_signup_in.service.UserService;
import cn.orzlinux.step1_signup_in.tools.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

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

    @PostMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        String idname = request.getParameter("idname");
        String password = request.getParameter("password");
        User user = userService.login(new User(idname,null,password));
        if (user==null) {
            return "redirect:/signin";
        }
        // 随便造一个session，放入
        String sess = MD5.getMd5(idname+"=^8&"+MD5.getMd5(password));
        request.getSession().setAttribute("userid", sess);
        model.addAttribute("userid",sess);
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
            boolean ret = userService.regist(new User(idname,nickname,password));
            if (!ret) {
                return "redirect:/signup";
            }
            // 随便造一个session，放入
            String sess = MD5.getMd5(idname+"=^8&"+MD5.getMd5(password));
            request.getSession().setAttribute("userid", sess);
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
