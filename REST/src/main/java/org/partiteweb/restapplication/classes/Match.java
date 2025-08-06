package org.partiteweb.restapplication.classes;

import java.time.LocalDate;

public class Match {

    private final String teamA;
    private final String teamB;
    private final LocalDate date;

    public Match(String teamA, String teamB, LocalDate date) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
    }

    public String getTeamA() {return teamA;}
    public String getTeamB() {return teamB;}
    public LocalDate getDate() {return date;}
}
