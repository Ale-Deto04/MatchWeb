package org.partiteweb.matchweb.classes.myUsers;

public class ScoreboardUser extends BasicUser {

    //Used for getting the scoreboard
    private int position;
    private int score;
    private String prize;
    private int counter;
    private int prizesWon;

    public ScoreboardUser(String name, String surname, String username, String role, int score, int counter, int prizesWon) {
        super(name, surname, username, role);
        this.position = 0;
        this.score = score;
        this.prize = null;
        this.counter = counter;
        this.prizesWon = prizesWon;
    }

    public boolean hasPrize() {
        return prize != null;
    }

    //Getter
    public int getScore() {
        return score;
    }

    public String getPrize() {
        if (hasPrize()) {
            return prize;
        }
        return "none";
    }

    public int getCounter() {return this.counter;}
    public int getPrizesWon() {return this.prizesWon;}
    public int getPosition() {return this.position;}

    //Setter
    public void setPrize(String prize) {
        this.prize = prize;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
