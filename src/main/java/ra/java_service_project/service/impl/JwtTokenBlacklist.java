package ra.java_service_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.java_service_project.model.entity.BlackList;
import ra.java_service_project.repository.BlackListRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtTokenBlacklist {
    // Dùng Set để lưu các token đã logout
    @Autowired
    private BlackListRepository jwtTokenBlacklist;


    public void addToBlacklist(String token) {
        BlackList blackList = new BlackList();
        blackList.setToken(token);
        blackList.setCreatedAt(LocalDateTime.now());
        jwtTokenBlacklist.save(blackList);

    }

    public boolean isBlacklisted(String token) {
        return jwtTokenBlacklist.existsByToken(token);

    }
}