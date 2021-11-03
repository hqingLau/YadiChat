package cn.orzlinux.step4_file_img.service;

import cn.orzlinux.step4_file_img.bean.TextMsg;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {
    public final static String FILE_STORE_PATH = "/home/hqinglau/YadiChat/uploadFiles";
    public Map<String,String> upload(MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String,String> result = new HashMap<>();
        String filename = UUID.randomUUID().toString().replace("-", "");
        String originalName = file.getOriginalFilename();
        filename = filename+originalName;

        Path path = Paths.get(FILE_STORE_PATH,filename);
        try {
            Files.copy(file.getInputStream(),path);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","false");
            return result;
        }
        result.put("status","true");
        result.put("type",isImage(new File(String.valueOf(path)))?"image":"file");
        result.put("origin_name",originalName);
        result.put("file_url",filename);
        return result;
    }

    public void download(String filename, HttpServletResponse response) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = response.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File(FILE_STORE_PATH,filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }

    /**
     * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。
     *
     * @param imageFile
     * @return
     */
    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }
}
