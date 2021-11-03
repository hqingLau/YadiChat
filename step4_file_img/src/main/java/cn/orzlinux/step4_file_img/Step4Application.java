package cn.orzlinux.step4_file_img;

import cn.orzlinux.step4_file_img.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class Step4Application {
    public static void main(String[] args) {

        SpringApplication.run(Step4Application.class, args);
        WebSocketServer.getInstance().run(10240);
    }
}
