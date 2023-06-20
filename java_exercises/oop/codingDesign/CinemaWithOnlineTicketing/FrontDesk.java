package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.sql.Date;

public class FrontDesk {
    
    Cinema cinema;                          // not sure if I really need this
    BookingSystem bookingSystem;

    public FrontDesk(Cinema cinema, BookingSystem bookingSystem) {
        this.cinema = cinema;
    }

    public boolean validateTicket(Ticket ticket, Date date) {
        boolean validation = bookingSystem.isValidTicket(ticket, date);
        System.out.println(String.format("Ticket [%s] for date [%s] is validated to [%b]", ticket.getId(), date, validation));
        if (validation) {
            bookingSystem.redeemTicket(ticket, date);
        }
        return validation;
    }
}
