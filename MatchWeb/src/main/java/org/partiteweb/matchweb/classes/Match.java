package org.partiteweb.matchweb.classes;

import java.time.LocalDate;

public class Match {
    private String teamA;
    private String teamB;
    private LocalDate date;

    //Setters
    public void setTeamA(String teamA) {this.teamA = teamA;}
    public void setTeamB(String teamB) {this.teamB = teamB;}
    public void setDate(String date) {this.date = LocalDate.parse(date);}

    //Getters
    public String getTeamA() {return teamA;}
    public String getTeamB() {return teamB;}
    public LocalDate getDate() {return date;}
}
