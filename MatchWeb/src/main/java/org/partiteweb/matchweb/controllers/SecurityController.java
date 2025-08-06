package org.partiteweb.matchweb.controllers;

import org.partiteweb.matchweb.classes.myUsers.SignupUser;
import org.partiteweb.matchweb.config.SecurityConfig;
import org.partiteweb.matchweb.services.ExternalMatchService;
import org.partiteweb.matchweb.services.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class SecurityController {

    private final SignupService signupService;
    private final ExternalMatchService externalMatchService;

    @Autowired
    SecurityController(SignupService signupService, ExternalMatchService externalMatchService) {
        this.signupService = signupService;
        this.externalMatchService = externalMatchService;
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
        if(auth!=null && auth.isAuthenticated()){
            return "error"; //user already authenticated
        }
        return "login";
    }

    @PostMapping("/login_error")
    public String loginError() {
        return "login_error";
    }

    @GetMapping("/signup")
    public String signup(Authentication auth, Model model) {
        if(auth!=null && auth.isAuthenticated()){
            return "error"; //user already authenticated
        }

        ArrayList<String> teams = externalMatchService.getTeams();
        model.addAttribute("teams", teams);
        return "signup";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam("firstname") String name,
                          @RequestParam("lastname") String surname,
                          @RequestParam("birthday") String sbirthday,
                          @RequestParam("email") String email,
                          @RequestParam("username") String username,
                          @RequestParam("pw") String password,
                          @RequestParam("sport") String sport,
                          @RequestParam("team") String team,
                          Model model, Authentication auth) {

        if(auth!=null && auth.isAuthenticated()){
            return "error";
        }

        LocalDate birthday = LocalDate.parse(sbirthday);
        if(signupService.userExists(username)) {
            System.err.println("[ERR] User already exists");
            model.addAttribute("userAlreadyExists", true);
            model.addAttribute("teams", externalMatchService.getTeams());
            return "signup";
        }

        if(!signupService.isValidInput(birthday, sport, password)) {
            System.out.println("[ERR] Sport not available or under aged or wrong password");
            return "error";
        }

        signupService.performRegistration(new SignupUser(name, surname, username, "ROLE_" + SecurityConfig.USER, birthday, email, password, sport, team));

        return "successfully_signed";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String returnPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + SecurityConfig.ADMIN))) {
            returnPage = "forward:adminDashboard";
        } else {
            returnPage = "forward:userDashboard";
        }
        model.addAttribute("name", authentication.getName());
        return returnPage;
    }

    @GetMapping("/userDashboard")
    public String userDashboard() {
        return "userDashboard";
    }

    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";
    }

    @GetMapping("/confirm_logout")
    public String confirm_logout() {
        return "confirm_logout";
    }

    @GetMapping("logout")
    public String logout() {
        return "logout";
    }
}
