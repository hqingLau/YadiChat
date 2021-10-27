package cn.orzlinux.step2_basic_communication.service;

import cn.orzlinux.step2_basic_communication.bean.TextMsg;
import cn.orzlinux.step2_basic_communication.dao.TextMsgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TextMsgService {
    @Autowired
    TextMsgDao textMsgDao;
    public int insertMsg(TextMsg textMsg) {
        return textMsgDao.insert(textMsg);
    }

    public List<TextMsg> getLast100Msg() {
        List<TextMsg> list = textMsgDao.getLast100Msg();
        Collections.reverse(list);
        return list;
    }
}
