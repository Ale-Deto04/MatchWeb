package org.partiteweb.matchweb.proxies;


import org.partiteweb.matchweb.classes.Match;
import org.partiteweb.matchweb.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@FeignClient(name = "match", url = "${name.service.url}", configuration = OpenFeignConfig.class)
public interface MatchProxy {

    @GetMapping("/teams")
    ArrayList<String> getTeams();

    @GetMapping("/calendar")
    ArrayList<Match> getCalendar();

    @PostMapping("/today_matches")
    ArrayList<Match> getTodayMatches(@RequestParam("date") String today_date);

    @PostMapping("/results")
    ArrayList<Integer> getResults(@RequestParam("date") String today_date);
}