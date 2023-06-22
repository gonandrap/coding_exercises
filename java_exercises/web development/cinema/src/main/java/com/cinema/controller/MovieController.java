package com.cinema.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Cinema;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Movie;

@RestController
@RequestMapping("movie")
public class MovieController {
    @Autowired
    private Cinema cinema;

    @PostMapping("/")
    public String addMovie(@RequestBody String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> bodyParams = springParser.parseMap(body);

        Movie newMovie = new Movie((String) bodyParams.get("title"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from, to;
        try {
            from = dateFormat.parse((String) bodyParams.get("fromDate"));
            to = dateFormat.parse((String) bodyParams.get("toDate"));
        } catch (ParseException e) {
            return String.format("{'error':'%s'}", "date bad format");
        }
        
        cinema.addNewMovie(newMovie, from, to);
        return "{status:'OK'}";
    }
}
