package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.repositories.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FinalScoreService {

    private final ExternalMatchService externalMatchService;
    private final ScoreboardRepository scoreboardRepository;

    @Autowired
    public FinalScoreService(ExternalMatchService externalMatchService, ScoreboardRepository scoreboardRepository) {
        this.externalMatchService = externalMatchService;
        this.scoreboardRepository = scoreboardRepository;
    }

    private void updateScoreboard(String username , int score) {
        scoreboardRepository.incrementUserScore(username, score);
    }

    private void updateGiornate(String username, LocalDate date) {
        scoreboardRepository.updateLastAccess(username, date);
        scoreboardRepository.incrementCounter(username);
    }

    public ArrayList<Integer> getResults(String username, List<Integer> user_guess, LocalDate date) throws Exception {
        int counter = 0;
        ArrayList<Integer> result = externalMatchService.getResults(date);

        if(result.size() != user_guess.size()) {
            throw new Exception("Results do not match");
        }

        for (int i = 0; i < user_guess.size(); i++) {
            if(Objects.equals(user_guess.get(i), result.get(i))) {
                counter++;
            }
        }
        updateScoreboard(username, counter);
        updateGiornate(username, date);

        return result;
    }
}
