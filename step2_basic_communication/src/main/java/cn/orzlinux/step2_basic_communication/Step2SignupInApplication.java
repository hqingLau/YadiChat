package cn.orzlinux.step2_basic_communication;

import cn.orzlinux.step2_basic_communication.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Step2SignupInApplication {
    public static void main(String[] args) {

        SpringApplication.run(Step2SignupInApplication.class, args);
        WebSocketServer.getInstance().run(10240);
    }

}
