package com.github.activity.githubactivity.controller;

import com.github.activity.githubactivity.service.GithubActivityService;
import org.springframework.stereotype.Component;

@Component
public class ActivityController {

    private final GithubActivityService service;

    public ActivityController(GithubActivityService service) {
        this.service = service;
    }

    public void fetchAndPrintActivity(String username) {
        try {
            var activities = service.getUserActivity(username);
            activities.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("❌ " + e.getMessage());
        }
    }
}
