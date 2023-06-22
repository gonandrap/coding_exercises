package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.text.ParseException;
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

    public Cinema() {
        rooms = new ArrayList<Room>();
        bookingSystem = new BookingSystem();
        initMovieScheduler();
    }

    public Cinema(List<Room> rooms) {
        this.rooms = new ArrayList<Room>();

        for(var room : rooms) {
            this.rooms.add(room);
        }

        bookingSystem = new BookingSystem();
        initMovieScheduler();        
    }

    private void initMovieScheduler() {
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

    /**
     * Testing wise
     */
    public BookingSystem getbBookingSystem() {
        return bookingSystem;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public static void main(String[] args) throws ParseException {
        Room room = new Room("room1", 10, 10);
        Room room2 = new Room("room2", 20, 10);

        ArrayList<Room> rooms = new ArrayList<Room>() {
            {
                add(room);
                add(room2);
            }
        };

        Cinema cinema = new Cinema(rooms);
        Movie movie1 = addMovie(cinema, "Matrix 1", "2023-06-01", "2023-07-01");
        Movie movie2 = addMovie(cinema, "Matrix 2", "2023-08-01", "2023-09-01");

        BookingSystem booking = cinema.getbBookingSystem();
        Ticket ticketMatrix1 = booking.bookTicket(movie1.getTitle(), "2023-06-15", TimeOfDay.EVENING, room2.getName(), 5, 3);
        booking.bookTicket(movie1.getTitle(), "2024-06-15", TimeOfDay.NIGHT, room2.getName(), 7, 3);
        booking.bookTicket(movie2.getTitle(), "2023-06-13", TimeOfDay.NIGHT, room2.getName(), 7, 3);
        Ticket ticketMatrix2 = booking.bookTicket(movie2.getTitle(), "2023-08-13", TimeOfDay.NIGHT, room2.getName(), 10, 3);

        booking.printStatus();

        FrontDesk frontDesk = new FrontDesk(cinema, booking);
        frontDesk.validateTicket(ticketMatrix1, "2023-06-15");
        frontDesk.validateTicket(ticketMatrix1, "2023-06-15");
        frontDesk.validateTicket(ticketMatrix2, "2023-08-14");
        frontDesk.validateTicket(ticketMatrix2, "2023-08-13");

        booking.printStatus();
    }

    private static Movie addMovie(Cinema cinema, String movieName, String fromDate, String toDate) throws ParseException {
        Movie movie = new Movie(movieName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date from = dateFormat.parse(fromDate);
        Date to = dateFormat.parse(toDate);
        cinema.addNewMovie(movie, from, to);
        return movie;
    }
}
