package com.tempo.worklogs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LoadWorlogs {

    Logger logger = LoggerFactory.getLogger(LoadWorlogs.class);

    private static final String RESOURCE_URI = "https://api.tempo.io/4/worklogs";

    @Autowired
    private WebClient webClient;

    @Scheduled(cron = "0 0 1 * *")
    public void logWorkLogsServiceResponse() {

        webClient.get()
            .uri(RESOURCE_URI)
            .retrieve()
            .bodyToMono(String.class)
            .map(string -> "We retrieved the following resource using Client Credentials Grant Type: " + string)
            .subscribe(logger::info);
    }

}