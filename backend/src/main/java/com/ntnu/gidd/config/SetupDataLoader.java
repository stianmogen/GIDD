package com.ntnu.gidd.config;

import java.util.Arrays;
import java.util.Collections;

import com.ntnu.gidd.model.TrainingLevel;
import com.ntnu.gidd.model.User;
import com.ntnu.gidd.repository.TrainingLevelRepository;
import com.ntnu.gidd.repository.UserRepository;
import com.ntnu.gidd.util.TrainingLevelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TrainingLevelRepository trainingLevelRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        User account = new User();
        account.setEmail("admin@test.com");
        account.setPassword(encoder.encode("admin"));

        if (trainingLevelRepository.findTraningLevelByLevel(TrainingLevelEnum.High).isEmpty()){
            trainingLevelRepository.save(TrainingLevel.builder().id(1l).level(TrainingLevelEnum.High).build());
        }
        if (trainingLevelRepository.findTraningLevelByLevel(TrainingLevelEnum.Medium).isEmpty()){
            trainingLevelRepository.save(TrainingLevel.builder().id(2l).level(TrainingLevelEnum.Medium).build());
        }
        if (trainingLevelRepository.findTraningLevelByLevel(TrainingLevelEnum.Low).isEmpty()){
            trainingLevelRepository.save(TrainingLevel.builder().id(3l).level(TrainingLevelEnum.Low).build());
        }
        if(userRepository.findByEmail(account.getEmail()).isEmpty()){
            log.info("[x] Preloading {}", userRepository.save(account));
        }

        alreadySetup = true;
    }
}