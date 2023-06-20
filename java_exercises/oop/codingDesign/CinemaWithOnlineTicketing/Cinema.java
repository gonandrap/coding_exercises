package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class Cinema {
    List<Room> rooms;
    MovieScheduler movieScheduler;
    BookingSystem bookingSystem;

    public Cinema(List<Room> rooms) {
        this.rooms = rooms;
        bookingSystem = new BookingSystem();
        
        List<TimeOfDay> times = new ArrayList<TimeOfDay>() {
            {
                add(TimeOfDay.AFTERNOON);
                add(TimeOfDay.EVENING);
                add(TimeOfDay.NIGHT);
            } 
        };
        movieScheduler = new MovieScheduler(rooms, times);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addNewMovie(Movie movie, Date from, Date to) {
        for (FunctionScheduled movieScheduled : movieScheduler.scheduleMovie(movie, from, to)) {
            bookingSystem.startOfferingMovie(movieScheduled);
        }
    }


    public static void main(String[] args) {
        Room room = new Room("room1", 10, 10);
        Room room2 = new Room("room2", 20, 10);

        ArrayList<Room> rooms = new ArrayList<Room>() {
            {
                add(room);
                add(room2);
            }
        };

        Cinema cinema = new Cinema(rooms);
        Movie movie1 = new Movie("Matrix 1");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from;
        Date to;
        try {
            from = dateFormat.parse("2023-06-01");
            to = dateFormat.parse("2023-07-01");
            cinema.addNewMovie(movie1, from, to);
        } catch (Exception e) {
            System.out.println("Error parsing date");
        }
    }
}
