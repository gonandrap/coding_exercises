package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrontDesk {
    
    Cinema cinema;                          // not sure if I really need this
    BookingSystem bookingSystem;

    public FrontDesk(Cinema cinema, BookingSystem bookingSystem) {
        this.cinema = cinema;
        this.bookingSystem = bookingSystem;
    }

    public boolean validateTicket(String ticketId, Date date) {
        boolean validation = bookingSystem.isValidTicket(ticketId, date);
        System.out.println(String.format("Ticket [%s] for date [%s] is validated to [%b]", ticketId, date, validation));
        if (validation) {
            bookingSystem.redeemTicket(ticketId, date);
        }
        return validation;
    }

    public boolean validateTicket(Ticket ticket, Date date) {
        return validateTicket(ticket.getId(), date);
    }

    public boolean validateTicket(String ticketId, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return validateTicket(ticketId, formatter.parse(date));
    }

    public boolean validateTicket(Ticket ticket, String date) throws ParseException {
        return validateTicket(ticket.getId(), date);
    }

}
