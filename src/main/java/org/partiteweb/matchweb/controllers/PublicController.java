package org.partiteweb.matchweb.controllers;

import org.partiteweb.matchweb.services.ExternalMatchService;
import org.partiteweb.matchweb.services.RatingService;
import org.partiteweb.matchweb.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PublicController {

    private final ExternalMatchService externalMatchService;
    private final RatingService ratingService;
    private final NewsService newsService;

    @Autowired
    public PublicController(ExternalMatchService externalMatchService, RatingService ratingService, NewsService newsService) {
        this.externalMatchService = externalMatchService;
        this.ratingService = ratingService;
        this.newsService = newsService;
    }

    @GetMapping("/index")
    public String home(Authentication auth, Model model) {
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "index";
    }

    @GetMapping("/sponsor")
    public String sponsor(Authentication auth, Model model) {
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "sponsor";
    }

    @GetMapping("/ratings")
    public String recensioni(Authentication auth, Model model) {
        model.addAttribute("ratings", ratingService.getRatings());
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "ratings";
    }

    //Sports
    @GetMapping("/baseball")
    public String baseball(Authentication auth, Model model) {
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "baseball";
    }

    @GetMapping("/cricket")
    public String cricket(Authentication auth, Model model) {
        model.addAttribute("teams", externalMatchService.getTeams());
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "cricket";
    }

    @GetMapping("/padel")
    public String padel(Authentication auth, Model model) {
        model.addAttribute("auth", auth != null && auth.isAuthenticated());
        return "padel";
    }

    //News
    @GetMapping("/news")
    @ResponseBody
    public String news() {
        return newsService.getNextNews();
    }
}
