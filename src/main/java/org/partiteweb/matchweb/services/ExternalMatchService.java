package org.partiteweb.matchweb.services;

import org.partiteweb.matchweb.classes.Match;
import org.partiteweb.matchweb.proxies.MatchProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ExternalMatchService {

    private final MatchProxy matchProxy;

    @Autowired
    public ExternalMatchService(@Lazy MatchProxy matchProxy) {
        this.matchProxy = matchProxy;
    }

    public ArrayList<String> getTeams() {return  matchProxy.getTeams();}
    public ArrayList<Match> getCalendar() {return  matchProxy.getCalendar();}
    public ArrayList<Match> getTodayMatches(LocalDate date) {return matchProxy.getTodayMatches(date.toString());}
    public ArrayList<Integer> getResults(LocalDate date) {return matchProxy.getResults(date.toString());}
}
