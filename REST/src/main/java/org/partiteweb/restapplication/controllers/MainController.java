package org.partiteweb.restapplication.controllers;

import org.partiteweb.restapplication.classes.Match;
import org.partiteweb.restapplication.services.CalendarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MainController {

    private final CalendarManager calendarManager;

    @Autowired
    public MainController(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
    }

    @GetMapping("/calendar")
    public ArrayList<Match> getCalendar() {
        return calendarManager.getCalendar();
    }

    @GetMapping("/teams")
    public ArrayList<String> getTeams() {
        return calendarManager.getTeams();
    }

    @PostMapping("/today_matches")
    public ArrayList<Match> getTodayMatches(@RequestParam("date") String date) {
        return calendarManager.getTodayMatches(date);
    }

    @PostMapping("/results")
    public ArrayList<Integer> getResults(@RequestParam("date") String date) {
        return calendarManager.getTodayResults(date);
    }
}
