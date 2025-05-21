package com.github.activity.githubactivity.exception;

public class GithubActivityException extends RuntimeException {

    public GithubActivityException(String message) {
        super(message);
    }

    public GithubActivityException(String message, Throwable cause) {
        super(message, cause);
    }
}
