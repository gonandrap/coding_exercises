package com.cinema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Cinema;

@Configuration
@ComponentScan("java_exercises.oop.codingDesign.CinemaWithOnlineTicketing")
public class ExternalBeanConfiguration {
    @Bean
    public Cinema cinemaBean() {
        return new Cinema();
    }
}
