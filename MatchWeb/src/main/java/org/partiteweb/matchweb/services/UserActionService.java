package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.Rating;
import org.partiteweb.matchweb.classes.myUsers.FullUser;
import org.partiteweb.matchweb.repositories.RatingRepository;
import org.partiteweb.matchweb.repositories.ScoreboardRepository;
import org.partiteweb.matchweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserActionService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ScoreboardRepository scoreboardRepository;

    @Autowired
    public UserActionService(UserRepository userRepository, RatingRepository ratingRepository, ScoreboardRepository scoreboardRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.scoreboardRepository = scoreboardRepository;
    }

    public void addRating(Rating rating) {
        ratingRepository.addRating(rating);
    }

    public void changePassword(String username, String password) {
        userRepository.changePassword(username, password);
    }

    public FullUser showUser(String username) {
        FullUser user = userRepository.showUser(username);
        user.setPosition(scoreboardRepository.getPositionOf(username));
        return user;
    }

    public boolean canPlayAgain(String username) {
        return scoreboardRepository.getLastAccess(username).isBefore(LocalDate.now());
    }
}
