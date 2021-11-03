package cn.orzlinux.step4_file_img.controller;

import cn.orzlinux.step4_file_img.Utils.CookieUtils;
import cn.orzlinux.step4_file_img.bean.GOFWithNick;
import cn.orzlinux.step4_file_img.bean.TextMsg;
import cn.orzlinux.step4_file_img.bean.User;
import cn.orzlinux.step4_file_img.server.UserSession;
import cn.orzlinux.step4_file_img.service.GroupOrFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GroupOrFriendController {
    @Autowired
    GroupOrFriendService service;

    @RequestMapping("/new-room")
    public String signin() {
        return "newRoom";
    }

    @RequestMapping("/join-room")
    public String joinRoom() {
        return "joinRoom";
    }

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }

    @PostMapping("/create_room")
    public String create_room(HttpServletRequest request, HttpServletResponse response) {
        String nickname = request.getParameter("nickname");
        Cookie cookie = CookieUtils.getCookie(request.getCookies(), "user");

        if (cookie == null) {
            return "redirect:/home";
        }

        User user = UserSession.get(cookie.getValue());
        if (user == null) {
            return "redirect:/home";
        }
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss_");
        String groupName = format.format(now) + user.getIdName();
        service.createNewGroup(user.getIdName(), groupName, nickname);
        return "redirect:/chat";
    }

    @ResponseBody
    @RequestMapping(value = "/group/getLast100Msg",produces = {"application/json;charset=UTF-8"})
    public List<TextMsg> getLast100Msg(HttpServletRequest request, HttpServletResponse response) {
        return service.getLast100Msg(request.getParameter("gid"));
    }

    @ResponseBody
    @RequestMapping(value = "/group/getbelonggroup",produces = {"application/json;charset=UTF-8"})
    public List<GOFWithNick> getbelonggroup(HttpServletRequest request, HttpServletResponse response) {
        return service.getUserBelongGroup(request.getParameter("uid"));
    }


    @RequestMapping("/join_room")
    public String join_room(HttpServletRequest request, HttpServletResponse response) {
        service.joinRoom(request.getParameter("uid"),request.getParameter("gid"));
        return "redirect:/chat";
    }
}
