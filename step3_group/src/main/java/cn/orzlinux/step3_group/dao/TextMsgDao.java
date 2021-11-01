package cn.orzlinux.step3_group.dao;

import cn.orzlinux.step3_group.bean.TextMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextMsgDao {
    int insert(TextMsg msg);
    List<TextMsg> getLast100Msg();
}
