package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.client", "ru.practicum.mainservice"})
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}