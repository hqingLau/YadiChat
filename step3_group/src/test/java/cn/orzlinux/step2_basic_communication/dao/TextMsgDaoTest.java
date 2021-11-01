package cn.orzlinux.step3_group.dao;

import cn.orzlinux.step3_group.bean.TextMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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