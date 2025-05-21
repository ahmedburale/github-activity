package com.github.activity.githubactivity.domain.parser;

import com.github.activity.githubactivity.domain.model.GithubEvent;

public class EventFormatter {

    public static String format(GithubEvent event) {
        return switch (event.getType()) {
            case "PushEvent" -> "Pushed to " + event.getRepoName();
            case "WatchEvent" -> "Starred " + event.getRepoName();
            case "IssuesEvent" -> event.getAction() + " issue in " + event.getRepoName();
            default -> event.getType() + " in " + event.getRepoName();
        };
    }
}
