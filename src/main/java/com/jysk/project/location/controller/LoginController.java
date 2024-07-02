package com.jysk.project.location.controller;

import com.jysk.project.location.domain.Location;
import com.jysk.project.location.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    private LocationService locationService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This will resolve to src/main/resources/templates/login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        logger.info("User logged in with username: {} ", username);
        model.addAttribute("username", username);
        return "search"; // Redirect to search page after login
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search"; // This will resolve to src/main/resources/templates/search.html
    }


    @PostMapping("/search")
    public String searchLocation(@RequestParam String query, Model model) {
        Location location = locationService.searchLocation(query);
        if (location != null) {
            model.addAttribute("result", location);
        } else {
            model.addAttribute("error", "Location not found");
        }
        return "search"; // This will resolve to src/main/resources/templates/search.html
    }
}

