package org.partiteweb.matchweb.controllers;

import org.partiteweb.matchweb.classes.Rating;
import org.partiteweb.matchweb.services.FinalScoreService;
import org.partiteweb.matchweb.services.ExternalMatchService;
import org.partiteweb.matchweb.services.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class UserController {

    private final ExternalMatchService externalMatchService;
    private final UserActionService userActionService;
    private final FinalScoreService finalScoreService;

    @Autowired
    public UserController(ExternalMatchService externalMatchService, UserActionService userActionService, FinalScoreService finalScoreService) {
        this.externalMatchService = externalMatchService;
        this.userActionService = userActionService;
        this.finalScoreService = finalScoreService;
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {
        model.addAttribute("calendar", externalMatchService.getCalendar());
        model.addAttribute("date", LocalDate.now());
        return "calendar";
    }

    @GetMapping("/addRating")
    public String addRating() {
        return "addRating";
    }

    @PostMapping("/perform_add")
    public String addRate( Authentication authentication,
                           @RequestParam(name = "rate", defaultValue = "1") String rate,
                           @RequestParam("text") String text) {
        int irate = 1;
        try {
            irate = Integer.parseInt(rate);
        } catch (NumberFormatException e) {
            //keep irate = 1
        }
        userActionService.addRating(new Rating(irate, authentication.getName(), LocalDate.now().toString(), text));
        return "redirect:/ratings";
    }

    @PostMapping("/change_password")
    public String performChangePassword(Authentication authentication, @RequestParam("pw") String password, RedirectAttributes redirectAttributes) {
        userActionService.changePassword(authentication.getName(), password);
        return "redirect:/dashboard";
    }

    @GetMapping("/account")
    public String account( Authentication authentication, Model model) {
        model.addAttribute("user", userActionService.showUser(authentication.getName()));
        return "account";
    }

    @GetMapping("/play")
    public String play(Authentication authentication, Model model) {
        if(userActionService.canPlayAgain(authentication.getName())) {
            model.addAttribute("matches", externalMatchService.getTodayMatches(LocalDate.now()));
            model.addAttribute("date", LocalDate.now());
            return "play";
        }
        model.addAttribute("cannotPlayAgain", true);
        System.out.println("[ERR] User cannot play again");
        return "userDashboard";
    }

    @PostMapping("/results")
    @ResponseBody
    public ArrayList<Integer> results(Authentication authentication, @RequestBody ArrayList<Integer> user_guess) {
        try {
            return finalScoreService.getResults(authentication.getName(), user_guess, LocalDate.now());
        } catch (Exception e) {
            System.err.println("[ERR] " + e.getMessage());
            return null;
        }
    }
}
