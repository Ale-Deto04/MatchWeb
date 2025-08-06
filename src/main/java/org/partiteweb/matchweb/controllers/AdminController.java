package org.partiteweb.matchweb.controllers;

import org.partiteweb.matchweb.services.AdminActionService;
import org.partiteweb.matchweb.services.AssignPrizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminActionService adminActionService;
    private final AssignPrizesService assignPrizesService;

    @Autowired
    public AdminController(AdminActionService adminActionService, AssignPrizesService assignPrizesService) {
        this.adminActionService = adminActionService;
        this.assignPrizesService = assignPrizesService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", adminActionService.getAllUsers());
        return "users";
    }

    @GetMapping("/scoreboard")
    public String scoreboard(Model model) {
        model.addAttribute("scoreboard", assignPrizesService.getScoreboard());
        return "scoreboard";
    }

    @PostMapping("/promote")
    public String promote(@RequestParam("user_to_promote") String username) {
        adminActionService.promoteUser(username);
        return "redirect:/users";
    }

    @GetMapping("/podium")
    public String podium(Model model) {
        model.addAttribute("podium", assignPrizesService.getPodium());
        return "podium";
    }
}
