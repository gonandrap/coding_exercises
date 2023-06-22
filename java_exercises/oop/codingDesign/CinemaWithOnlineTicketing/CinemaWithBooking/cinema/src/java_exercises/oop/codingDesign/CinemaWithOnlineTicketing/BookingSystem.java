package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    Map<String, Room> availableRooms;
 
    BookingSystem() {
        moviesOffered = new HashMap<String, FunctionScheduled>();
        ticketsSold = new HashMap<UUID, Ticket>();
        ticketsRedeemed = new HashSet<UUID>();
        functionAvailability = new HashMap<String, AvailabilityCounter>();
        availableRooms = new HashMap<String, Room>();
    }

    /*
     * NOTE : what if a movie's projection extends over the initial start/end date? Let's not support that use case for now
     */
    void startOfferingMovie(FunctionScheduled functionScheduled) {
        availableRooms.putIfAbsent(functionScheduled.getRoom().getName(), functionScheduled.getRoom());
        moviesOffered.put(uniqueIdentifier(functionScheduled), functionScheduled);
    }


    public Ticket bookTicket(String movieName, String date, TimeOfDay timeOfDay, String roomName, int row, int col) throws ParseException { 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return bookTicket(movieName, dateFormat.parse(date), timeOfDay, roomName, row, col);
    }

    public Ticket bookTicket(String movieName, Date date, TimeOfDay timeOfDay, String roomName, int row, int col) {
        String id = uniqueIdentifier(movieName, timeOfDay);
        Room room = availableRooms.get(roomName);
        if  (isAvailable(id, room, row, col)) {
            if (validateDate(id, date, timeOfDay) == true) {
                Ticket newTicket = new Ticket(movieName, date, timeOfDay, roomName, row, col);
                safeGet(id, room).markAsOccupied(row, col);
                ticketsSold.put(newTicket.getId(), newTicket);
                return newTicket;
            } else {
                System.out.println(String.format("Couldnt validate date [%s] for movie [%s]", date.toString(), movieName));
                return null;
            }
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

    public void printStatus() {
        System.out.println("Movies being offered");
        for(Map.Entry<String, FunctionScheduled> entry : moviesOffered.entrySet()) {
            System.out.println(String.format("\tMovie [%s] offered in room [%s]", entry.getKey(), entry.getValue().getRoom().getName()));
        }

        System.out.println("Tickets sold");
        for (Map.Entry<UUID, Ticket> entry : ticketsSold.entrySet()) {
            System.out.println(String.format("\tTicket for movie {%s} was sold", entry.getValue().getMovieName()));
        }

        System.out.println("Tickets redeemed");
        for (UUID redeemed : ticketsRedeemed) {
            System.out.println(String.format("\tTicket {%s} for movie {%s} was sold", redeemed, ticketsSold.get(redeemed).getMovieName()));
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

    private String uniqueIdentifier(FunctionScheduled functionScheduled) {
        return uniqueIdentifier(functionScheduled.getMovie().getTitle(), functionScheduled.getTimeOfDay());
    }

    private String uniqueIdentifier(String movieName, TimeOfDay timeOfDay) {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //return String.format("%s_%s_%s_%s", movie.getTitle(), formatter.format(date), timeOfDay.toString(), room.getName());
        return String.format("%s_%s", movieName, timeOfDay.toString());
    }

    private boolean validateDate(String movieId, Date date, TimeOfDay timeOfDay) {
        FunctionScheduled movieOffered = moviesOffered.get(movieId);
        return movieOffered != null &&
                validateDateInRange(date, movieOffered.getInitDate(), movieOffered.endDate) &&
                movieOffered.getTimeOfDay() == timeOfDay;
    }

    private boolean isAvailable(String id, Room room, int row, int col) {
        AvailabilityCounter counter = functionAvailability.get(id);
        if (room != null && counter == null) {
            counter = new AvailabilityCounter(room.getNumberRows(), room.getNumberCols());
            functionAvailability.put(id, counter);
        }
        return room != null && counter != null && counter.isAvailable(row, col);
    }

    private boolean validateDateInRange(Date bookingForDate, Date from, Date to) {
        return bookingForDate.compareTo(from) >= 0 && bookingForDate.compareTo(to) <= 0;
    }

    private boolean validateTicketDate(Ticket ticket, Date today) {
        return ticket.getDate().compareTo(today) == 0;
    }
}
