package cn.orzlinux.step2_basic_communication.dao;

import cn.orzlinux.step2_basic_communication.bean.TextMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextMsgDao {
    int insert(TextMsg msg);
    List<TextMsg> getLast100Msg();
}
