package cn.orzlinux.step3_group.tools;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5 {
    private final static String salt = "hqinglau@is;a^good_man";

    /**
     * 生成32位的MD5
     * @param msg
     * @return
     */
    public static String getMd5(String msg) {
        String base = msg+"/"+salt;
        return DigestUtils.md5DigestAsHex(base.getBytes(StandardCharsets.UTF_8));
    }
}
