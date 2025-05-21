package com.github.activity.githubactivity.service;

import com.github.activity.githubactivity.domain.model.GithubEvent;
import com.github.activity.githubactivity.domain.parser.EventFormatter;
import com.github.activity.githubactivity.infrastructure.GithubApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubActivityService {

    private final GithubApiClient apiClient;

    public GithubActivityService(GithubApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<String> getUserActivity(String username) {
        List<GithubEvent> events = apiClient.fetchEvents(username);
        return events.stream().map(EventFormatter::format).toList();
    }
}
