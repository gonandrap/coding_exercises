package com.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.BookingSystem;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Cinema;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.FrontDesk;

@Configuration
@ComponentScan("java_exercises.oop.codingDesign.CinemaWithOnlineTicketing")
public class ExternalBeanConfiguration {
    @Bean
    public Cinema cinemaBean() {
        return new Cinema();
    }

    @Autowired
    @Bean
    public FrontDesk frontDeskBean(Cinema cinema) {
        return new FrontDesk(cinema, cinema.getbBookingSystem());
    }
}
