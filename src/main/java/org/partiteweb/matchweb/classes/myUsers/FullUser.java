package org.partiteweb.matchweb.classes.myUsers;

import java.time.LocalDate;

public class FullUser extends InfoUser {

    //Used for show account
    private final ScoreboardUser scoreboardUser; //composition

    public FullUser(String name, String surname, String username, String role, LocalDate birthday, String email, int score, int counter, int prizesWon) {
        super(name, surname, username, role, birthday, email);
        scoreboardUser = new ScoreboardUser(name, surname, username, role, score, counter, prizesWon);
    }

    //Getter
    public int getScore() {return scoreboardUser.getScore();}
    public int getCounter() {return scoreboardUser.getCounter();}
    public int getPrizesWon() {return scoreboardUser.getPrizesWon();}
    public int getPosition() {return scoreboardUser.getPosition();}

    //Setter
    public void setPosition(int position) {
        scoreboardUser.setPosition(position);
    }
}
