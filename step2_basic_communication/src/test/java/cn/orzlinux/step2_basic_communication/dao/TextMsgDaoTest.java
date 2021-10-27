package cn.orzlinux.step2_basic_communication.dao;

import cn.orzlinux.step2_basic_communication.bean.TextMsg;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TextMsgDaoTest {
    @Resource
    TextMsgDao dao;

    @Test
    public void insert() {
        TextMsg textMsg = new TextMsg("123","123","123",new Date());
        dao.insert(textMsg);
    }

    @Test
    public void query() {
        List<TextMsg> msgs = dao.getLast100Msg();
        for (TextMsg msg:msgs) {
            System.out.println(msg);
        }
    }
}