package com.cinema.controller;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Cinema;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.TimeOfDay;

@RestController
@RequestMapping("ticket")
public class TicketController {
    
    @Autowired
    private Cinema cinema;

    @PostMapping("/")
    public String buyTicket(@RequestBody String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> bodyParams = springParser.parseMap(body);

        String movieName = (String) bodyParams.get("movieName");
        String date = (String) bodyParams.get("date");
        TimeOfDay time = TimeOfDay.valueOf((String) bodyParams.get("time"));
        String roomName = (String) bodyParams.get("room");
        int row = (int) bodyParams.get("row");
        int col = (int) bodyParams.get("col");
        try {
            cinema.getbBookingSystem().bookTicket(movieName, date, time, roomName, row, col);
        } catch (ParseException e) {
            return String.format("{'error':'%s'}", "date bad format");
        }

        cinema.getbBookingSystem().printStatus();

        return "{OK}";
    }
}
