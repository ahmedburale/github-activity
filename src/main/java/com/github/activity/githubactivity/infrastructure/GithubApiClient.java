package com.github.activity.githubactivity.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.activity.githubactivity.domain.model.GithubEvent;
import com.github.activity.githubactivity.exception.GithubActivityException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class GithubApiClient {

    private static final String URL_TEMPLATE = "https://api.github.com/users/ahmedburale/events";
    private final HttpClient httpClient;

    public GithubApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<GithubEvent> fetchEvents(String username) {
        String url = String.format(URL_TEMPLATE, username);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 404) {
                throw new GithubActivityException("GitHub user not found: " + username);
            } else if (response.statusCode() != 200) {
                throw new GithubActivityException("Failed to fetch events. Status: " + response.statusCode());
            }

            return parseEvents(response.body());

        } catch (IOException | InterruptedException e) {
            throw new GithubActivityException("Error fetching data from GitHub", e);
        }
    }

    private List<GithubEvent> parseEvents(String json) {
        List<GithubEvent> events = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            for (JsonNode item : node) {
                String type = item.get("type").asText();
                String repo = item.get("repo").get("name").asText();
                String action = item.path("payload").path("action").asText("");
                events.add(new GithubEvent(type, repo, action));
            }

        } catch (Exception e) {
            throw new GithubActivityException("Invalid JSON format from GitHub", e);
        }

        return events;
    }
}
