package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.myUsers.SignupUser;
import org.partiteweb.matchweb.components.Validator;
import org.partiteweb.matchweb.repositories.ScoreboardRepository;
import org.partiteweb.matchweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final ScoreboardRepository scoreboardRepository;
    private final Validator validator;

    @Autowired
    SignupService(UserRepository userRepository, ScoreboardRepository scoreboardRepository, Validator validator) {
        this.userRepository = userRepository;
        this.scoreboardRepository = scoreboardRepository;
        this.validator = validator;
    }

    public boolean userExists(String username) {
        return userRepository.userExists(username);
    }

    public boolean isValidInput(LocalDate date, String sport, String pw) {
        return validator.isAvailableSport(sport) && validator.isOldEnough(date) && validator.isValidPw(pw);
    }

    public void performRegistration(SignupUser user) {
        userRepository.addUser(user);
        scoreboardRepository.initUserScore(user.getUsername());
        scoreboardRepository.initGiornate(user.getUsername());
        System.out.println("[INFO] User successfully registered!");
    }
}
