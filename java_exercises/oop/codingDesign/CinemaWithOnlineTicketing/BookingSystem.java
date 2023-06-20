package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BookingSystem {
    Map<String, FunctionScheduled> moviesOffered;        // key = movie name, value = scheduled movie
    Map<UUID, Ticket> ticketsSold;
    Set<UUID> ticketsRedeemed;
    Map<String, AvailabilityCounter> functionAvailability;      // key = movieTittle_date_time_room
 
    BookingSystem() {
        moviesOffered = new HashMap<String, FunctionScheduled>();
        ticketsSold = new HashMap<UUID, Ticket>();
        ticketsRedeemed = new HashSet<UUID>();
        functionAvailability = new HashMap<String, AvailabilityCounter>();
    }

    /*
     * NOTE : what if a movie's projection extends over the initial start/end date? Let's not support that use case for now
     */
    void startOfferingMovie(FunctionScheduled functionScheduled) {
        moviesOffered.put(functionScheduled.getId(), functionScheduled);
    }


    public Ticket bookTicket(Movie movie, Date date, TimeOfDay timeOfDay, Room room, int row, int col) {
        String id = uniqueIdentifier(movie, date, timeOfDay, room);
        if  (isAvailable(id, row, col)) {
            Ticket newTicket = new Ticket(movie, date, timeOfDay, room, row, col);
            safeGet(id, room).markAsOccupied(row, col);
            ticketsSold.put(newTicket.getId(), newTicket);
            return newTicket;
        } else {
            return null;
        }
    }

    public boolean isValidTicket(Ticket ticket, Date date) {
        return (ticketsSold.containsKey(ticket.getId())) &&
                (ticketsRedeemed.contains(ticket.getId()) == false) &&
                validateTicketDate(ticket, date);
    }

    public void redeemTicket(Ticket ticket, Date date) {
        if (isValidTicket(ticket, date)) {
            ticketsRedeemed.add(ticket.getId());
        }
    }

    private AvailabilityCounter safeGet(String id, Room defaultRoom) {
        if (functionAvailability.containsKey(id)) {
            return functionAvailability.get(id);
        } else {
            functionAvailability.put(id, new AvailabilityCounter(defaultRoom.getNumberRows(), defaultRoom.getNumberCols()));
            return functionAvailability.get(id);
        }
    }

    private String uniqueIdentifier(Movie movie, Date date, TimeOfDay timeOfDay, Room room) {
        return String.format("%s_%s_%s_%s", movie.getTitle(), date.toString(), timeOfDay.toString(), room.getName());
    }

    private boolean isAvailable(String id, int row, int col) {
        AvailabilityCounter counter = functionAvailability.get(id);
        return counter != null && counter.isAvailable(row, col);
    }

    private boolean validateTicketDate(Ticket ticket, Date today) {
        return ticket.getDate().compareTo(today) == 0;
    }
}
