package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FunctionScheduled {
    Room room;
    Movie movie;
    Date initDate;
    Date endDate;
    TimeOfDay timeOfDay;

    public FunctionScheduled(Room room, Movie movie, Date initDate, Date endDate, TimeOfDay timeOfDay) {
        this.room = room;
        this.movie = movie;
        this.initDate = initDate;
        this.endDate = endDate;
        this.timeOfDay = timeOfDay;
    }

    public String getId() {
        return String.format("%s_%s", movie.getTitle(), timeOfDay.toString());
    }

    public Room getRoom() {
        return room;
    }

    public Movie getMovie() {
        return movie;
    }

    public Date getInitDate() {
        return initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

   public static List<FunctionScheduled> createForEntireDay(Room room, Movie movie, Date initDate, Date endDate) {
        List<FunctionScheduled> result = new ArrayList<FunctionScheduled>();

        result.add(new FunctionScheduled(room, movie, initDate, endDate, TimeOfDay.AFTERNOON));
        result.add(new FunctionScheduled(room, movie, initDate, endDate, TimeOfDay.EVENING));
        result.add(new FunctionScheduled(room, movie, initDate, endDate, TimeOfDay.NIGHT));

        return result;
   } 
}
