package cn.orzlinux.step4_file_img.controller;

import ch.qos.logback.core.util.FileUtil;
import cn.orzlinux.step4_file_img.bean.TextMsg;
import cn.orzlinux.step4_file_img.service.FileUploadService;
import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {
    @Autowired
    FileUploadService service;

    @ResponseBody
    @RequestMapping(value = "/yadiUploadFile",produces = {"application/json;charset=UTF-8"})
    public String YadiUploadFile(@RequestParam(value = "file",required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        return JSONUtils.toJSONString(service.upload(file,request));
    }

    @RequestMapping(value = "/file")
    public void download(@RequestParam("fileurl") String filename,HttpServletResponse response) throws IOException {
        String type = new MimetypesFileTypeMap().getContentType(new File(FileUploadService.FILE_STORE_PATH,filename));
        // 设置编码
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        service.download(filename, response);
    }
}
