package cn.orzlinux.step3_group.server;

import cn.orzlinux.step3_group.bean.User;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

public class UserSession {
    public String curUserSessionId;
    private UserSession() {}
    public static void put(String sessionId,User user) {
        userMap.put(sessionId,user);
    }

    public static User get(String sessionId) {
        return userMap.getOrDefault(sessionId,null);
    }

    private static ExpiringMap<String, User> userMap = ExpiringMap.builder().variableExpiration()
            .expiration(24*60, TimeUnit.MINUTES).expirationPolicy(ExpirationPolicy.CREATED).build();

    public static void update(String value) {
        userMap.put(value,userMap.get(value));
    }
}
