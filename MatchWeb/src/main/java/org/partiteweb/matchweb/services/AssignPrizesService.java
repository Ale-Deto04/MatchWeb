package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.myUsers.ScoreboardUser;
import org.partiteweb.matchweb.repositories.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AssignPrizesService {

    private final int PODIUM = 3;
    private final ScoreboardRepository scoreboardRepository;

    @Autowired
    public AssignPrizesService(ScoreboardRepository scoreboardRepository) {
        this.scoreboardRepository = scoreboardRepository;
    }

    public ArrayList<ScoreboardUser> getScoreboard() {
        return scoreboardRepository.getScoreboard();
    }

    public ArrayList<ScoreboardUser> getPodium() {

        ArrayList<ScoreboardUser> podium;
        try {
            podium = new ArrayList<>(scoreboardRepository.getScoreboard().subList(0, PODIUM)); //prende i primi 3
        } catch(IndexOutOfBoundsException e) {

            //Not enough player
            return null;
        }

        List<String> prizes = scoreboardRepository.getPrizes();
        Collections.shuffle(prizes); //gets random prizes

        for (int i = 0; i < PODIUM; i++) {
            podium.get(i).setPrize(prizes.get(i));
        }

        for (ScoreboardUser user : podium) {
            scoreboardRepository.incrementPrizesWon(user.getUsername());
        }

        scoreboardRepository.resetScoreboard(); //reset di scoreboard

        return podium;
    }
}
