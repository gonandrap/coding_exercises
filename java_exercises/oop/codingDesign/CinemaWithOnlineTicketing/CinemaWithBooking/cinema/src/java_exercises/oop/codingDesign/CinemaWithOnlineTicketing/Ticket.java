package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.util.Date;
import java.util.UUID;

public class Ticket {
    UUID id;                                // could be the equivalent to a QR code            
    String movieName;
    Date date;
    TimeOfDay timeOfDay;
    String roomName;
    int row;
    int column;

    public Ticket(String movieName, Date date, TimeOfDay timeOfDay, String roomName, int row, int column) {
        this.id = UUID.randomUUID();

        this.movieName = movieName;
        this.date = date;
        this.timeOfDay = timeOfDay;
        this.roomName = roomName;
        this.row = row;
        this.column = column;
    }

    public UUID getId() {
        return id;
    }

    public int getSeatRow() {
        return row;
    }

    public int getSeatColumn() {
        return column;
    }

    public Date getDate() {
        return date;
    }

    public String getMovieName() {
        return movieName;
    }
}
