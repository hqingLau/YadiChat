package cn.orzlinux.step3_group.service;

import cn.orzlinux.step3_group.bean.GOFWithNick;
import cn.orzlinux.step3_group.bean.TextMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@EnableTransactionManagement
public class GroupOrFriendServiceTest {
    @Autowired
    GroupOrFriendService service;

    @Test
    public void createNewGroup() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss_");
        String groupName = format.format(now)+"hqinglau";
        assert service.createNewGroup("hqinglau",groupName,"ghs交友群")==1;
        service.insertMsg(groupName,new TextMsg("hqinglau","hqhq","123",new Date()));
    }

    @Test
    public void removeGroup() {
        assert service.removeGroup("hqinglau","20211101_184935_hqinglau")==1;
    }

    @Test
    public void insertMsg() {
    }

    @Test
    public void getLast100Msg() {
        for (TextMsg i:service.getLast100Msg("20211101_201540_hqinglau")) {
            System.out.println(i);
        }
    }

    @Test
    public void joinRoom() {
        service.joinRoom("hqinglau2","20211101_190832_hqinglau");
    }
}