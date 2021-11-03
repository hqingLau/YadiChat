package cn.orzlinux.step4_file_img.dao;

import cn.orzlinux.step4_file_img.bean.TextMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextMsgDao {
    int insert(TextMsg msg);
    List<TextMsg> getLast100Msg();
}
