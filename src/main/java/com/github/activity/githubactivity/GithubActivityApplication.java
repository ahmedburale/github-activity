package com.github.activity.githubactivity;

import com.github.activity.githubactivity.controller.ActivityController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GithubActivityApplication implements CommandLineRunner {

    private final ActivityController controller;

    public GithubActivityApplication(ActivityController controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        SpringApplication.run(GithubActivityApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length != 1) {
            System.out.println("Usage: github-activity <github-username>");
            return;
        }

        controller.fetchAndPrintActivity(args[0]);
    }
}
