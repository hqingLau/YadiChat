package cn.orzlinux.step3_group.service;

import cn.orzlinux.step3_group.bean.TextMsg;
import cn.orzlinux.step3_group.dao.TextMsgDao;
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
