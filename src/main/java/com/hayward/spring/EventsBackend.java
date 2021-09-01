package com.hayward.spring;


import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.*;

@SpringBootApplication
@AllArgsConstructor
public class EventsBackend {


    public static void main(String[] args) {
        SpringApplication.run(EventsBackend.class, args);
    }
    @Scheduled(fixedRate = 1800*1000)
    void sendEmails(){

    }

    @EnableScheduling
    class SchedulingConfiguration {

    }
}
