package com.cinema.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Room;
import java_exercises.oop.codingDesign.CinemaWithOnlineTicketing.Cinema;

@RestController
@RequestMapping("room")
public class RoomController {
    @Autowired
    private Cinema cinema;

    @PostMapping("/")
    public String addRooms(@RequestBody String creationBody) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        List<Object> rooms = springParser.parseList(creationBody);
        List<Room> createdRooms = new ArrayList<Room>();
                
        for (Object roomAbstract : rooms) {
            Map<String,Object> roomDefinition = (Map<String,Object>) roomAbstract;
            createdRooms.add(new Room((String) roomDefinition.get("name"), (int) roomDefinition.get("numberRows"), (int) roomDefinition.get("numberCols")));
        }
        
        for(var room : createdRooms) {
            cinema.addRoom(room);
        }

        return "{status:'OK'}";      
    }

    @GetMapping("/{roomNumber}")
    public String roomInfo(@PathVariable int roomNumber) {
        
        List<Room> rooms = cinema.getRooms();
        
        return String.format("{'numberOfRooms':%d}", rooms.size());
    }
}
