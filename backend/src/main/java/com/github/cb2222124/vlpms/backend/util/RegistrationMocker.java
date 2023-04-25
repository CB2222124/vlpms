package com.github.cb2222124.vlpms.backend.util;

import com.github.cb2222124.vlpms.backend.model.Registration;
import com.github.cb2222124.vlpms.backend.repository.RegistrationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RegistrationMocker implements ApplicationRunner {

    private final RegistrationRepository registrationRepository;

    public RegistrationMocker(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        mockCurrent(1000);
        mockPrefix(1000);
        mockSuffix(1000);
    }

    private void mockCurrent(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            char char1 = (char) (random.nextInt(26) + 'A');
            char char2 = (char) (random.nextInt(26) + 'A');
            char char3 = (char) (random.nextInt(26) + 'A');
            char char4 = (char) (random.nextInt(26) + 'A');
            char char5 = (char) (random.nextInt(26) + 'A');
            String num1 = String.valueOf(random.nextInt(10));
            String num2 = String.valueOf(random.nextInt(10));
            String registration = "%s%s%s%s%s%s%s".formatted(char1, char2, num1, num2, char3, char4, char5);
            registrationRepository.save(new Registration(registration, "Current"));
        }
    }

    private void mockPrefix(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            char char1 = (char) (random.nextInt(26) + 'A');
            char char2 = (char) (random.nextInt(26) + 'A');
            char char3 = (char) (random.nextInt(26) + 'A');
            char char4 = (char) (random.nextInt(26) + 'A');
            int numberLength = random.nextInt(1, 4); //Produce more even spread of 1/2/3-digit numbers.
            String nums = String.valueOf(random.nextInt((int) Math.pow(10, numberLength)));
            String registration = "%s%s%s%s%s".formatted(char1, nums, char2, char3, char4);
            registrationRepository.save(new Registration(registration, "Prefix"));
        }
    }

    private void mockSuffix(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            char char1 = (char) (random.nextInt(26) + 'A');
            char char2 = (char) (random.nextInt(26) + 'A');
            char char3 = (char) (random.nextInt(26) + 'A');
            char char4 = (char) (random.nextInt(26) + 'A');
            int numberLength = random.nextInt(1, 4); //Produce more even spread of 1/2/3-digit numbers.
            String nums = String.valueOf(random.nextInt((int) Math.pow(10, numberLength)));
            String registration = "%s%s%s%s%s".formatted(char1, char2, char3, nums, char4);
            registrationRepository.save(new Registration(registration, "Suffix"));
        }
    }
}