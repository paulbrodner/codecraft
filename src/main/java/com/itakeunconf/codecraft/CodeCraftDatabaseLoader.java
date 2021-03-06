package com.itakeunconf.codecraft;

import com.itakeunconf.codecraft.model.PairingSession;
import com.itakeunconf.codecraft.model.Role;
import com.itakeunconf.codecraft.model.User;
import com.itakeunconf.codecraft.repository.PairingSessionRepository;
import com.itakeunconf.codecraft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@Profile("development")
public class CodeCraftDatabaseLoader {

    private final UserRepository userRepository;
    private final PairingSessionRepository pairingSessionRepository;

    @Autowired
    public CodeCraftDatabaseLoader(UserRepository userRepository, PairingSessionRepository pairingSessionRepository) {
        this.userRepository = userRepository;
        this.pairingSessionRepository = pairingSessionRepository;
    }

    @PostConstruct
    private void initDatabase() {
        User user = new User();
        user.setUserName("user01");
        user.setEmail("user@user.com");
        user.setPasswordHash(new BCryptPasswordEncoder().encode("user01"));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        PairingSession pairingSessionOne = new PairingSession();
        pairingSessionOne.setSessionName("Let's code together");
        pairingSessionOne.setLanguage("Java");
        pairingSessionOne.setPractice("TDD");
        pairingSessionOne.setLocation("ITAKE Product Development Room");
        pairingSessionOne.setAtTime(DateUtils.createNow().getTime());
        pairingSessionOne.setDuration("1 hour");
        pairingSessionOne.setCreator(savedUser);

        PairingSession pairingSessionTwo = new PairingSession();
        pairingSessionTwo.setSessionName("Ruby ninja pairing");
        pairingSessionTwo.setLanguage("Ruby");
        pairingSessionTwo.setPractice("Ninja pairing");
        pairingSessionTwo.setLocation("ITAKE Product Development Room");
        pairingSessionTwo.setAtTime(DateUtils.createNow().getTime());
        pairingSessionTwo.setCreator(savedUser);
        pairingSessionTwo.setDuration("1 hour");

        pairingSessionRepository.save(Arrays.asList(pairingSessionOne,pairingSessionTwo) );
    }
}
