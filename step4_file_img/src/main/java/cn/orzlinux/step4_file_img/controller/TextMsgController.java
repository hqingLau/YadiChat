package cn.orzlinux.step4_file_img.controller;

import cn.orzlinux.step4_file_img.bean.TextMsg;
import cn.orzlinux.step4_file_img.service.TextMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TextMsgController {
    @Autowired
    TextMsgService service;

    @ResponseBody
    @RequestMapping(value = "/textMsg/getLast100Msg",produces = {"application/json;charset=UTF-8"})
    public List<TextMsg> getLast100Msg() {
        return service.getLast100Msg();
    }
}
