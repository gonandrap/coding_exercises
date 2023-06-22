package com.cinema.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.FrontDesk;


@RestController
@RequestMapping("frontDesk")
public class FrontDeskController {
    @Autowired
    private FrontDesk frontDesk;

    @GetMapping("validate/{ticketId}/{date}")
    public String validate(@PathVariable String ticketId, @PathVariable String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateParsed;
        try {
            dateParsed = dateFormat.parse(date);
        } catch (ParseException e) {
            return "'result':'false'";
        }
        return String.format("{'result':'%b'}", frontDesk.validateTicket(ticketId, dateParsed));
    }
}
