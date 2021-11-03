package cn.orzlinux.step4_file_img.tools;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5Test {

    @Test
    public void getMd5() {
        System.out.println(MD5.getMd5("password"));
    }
}