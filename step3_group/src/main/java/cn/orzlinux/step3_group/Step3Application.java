package cn.orzlinux.step3_group;

import cn.orzlinux.step3_group.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class Step3Application {
    public static void main(String[] args) {

        SpringApplication.run(Step3Application.class, args);
        WebSocketServer.getInstance().run(10240);
    }
}
