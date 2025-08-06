package org.partiteweb.restapplication.services;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import org.partiteweb.restapplication.classes.Match;

@Service
public class CalendarManager {

    private final ArrayList<Match> calendar;
    private final ArrayList<String> teams;
    private final ArrayList<Integer> results;

    public CalendarManager() {
        this.teams = fetchTeams();
        this.calendar = initCalendar(teams);
        this.results = new ArrayList<>();
    }

    private ArrayList<String> fetchTeams() {
        try {
            ArrayList<String> teams = new ArrayList<>();

            InputStream inputStream = getClass().getResourceAsStream("/static/teams.txt"); //fetches data from teams.txt

            assert inputStream != null;
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                teams.add(scanner.nextLine());
            }
            scanner.close();
            return teams;

        } catch (Exception exc) {
            System.err.println("Err: failed to open teams.txt. File not found.");
            return null;
        }
    }

    private ArrayList<Match> initCalendar(ArrayList<String> teams) {

            ArrayList<Match> calendar = new ArrayList<>();

            if(teams == null) {
                System.err.println("Err: failed to init calendar.");
                return null;
            }

            Collections.shuffle(teams); //shuffles the team list

            //Randomly sets the date between today, yesterday and tomorrow
            Random random = new Random();

            calendar.add(new Match(teams.get(0), teams.get(1), LocalDate.now()));

            for (int i = 2; i < teams.size(); i+=2) {
                  int tmp = random.nextInt(3);
                  switch(tmp) {
                      case 0: calendar.add(new Match(teams.get(i), teams.get(i+1), LocalDate.now())); break;
                      case 1: calendar.add(new Match(teams.get(i), teams.get(i+1), LocalDate.now().plusDays(1))); break;
                      case 2: calendar.add(new Match(teams.get(i), teams.get(i+1), LocalDate.now().minusDays(1))); break;
                  }
            }

            teams.sort(null);
            return calendar;
    }

    public ArrayList<Match> getCalendar() {return calendar;}

    public ArrayList<Match> getTodayMatches(String date) {
        try {
            ArrayList<Match> todayMatches = new ArrayList<>();

            LocalDate todayDate = LocalDate.parse(date);
            for (Match m : calendar) {
                if (m.getDate().isEqual(todayDate)) {
                    todayMatches.add(m);
                }
            }
            return todayMatches;
        } catch (DateTimeParseException exc) {
            System.err.println("Err: failed to parse date: " + date);
            return null;
        }
    }

    private void initTodayResults(String date) {
        Random random = new Random();
        ArrayList<Match> todayMatches = getTodayMatches(date); //for every match assign a random value in [0, 3) range
        if (todayMatches != null) {
            for (int i = 0; i < todayMatches.size(); i++) {
                results.add(random.nextInt(3));
            }
        }
    }

    public ArrayList<String> getTeams() {
        return teams;
    }

    public ArrayList<Integer> getTodayResults(String date) {
        if (results.isEmpty()) {
            initTodayResults(date);
        }
        return results;
    }
}
