package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieScheduler {
    
    List<Room> rooms;
    List<TimeOfDay> timesOfDay;

    public MovieScheduler(List<Room> rooms, List<TimeOfDay> timesOfDay) {
        this.rooms = rooms;
        this.timesOfDay = timesOfDay;
    }

    public List<FunctionScheduled> scheduleMovie(Movie movie, Date startDate, Date endDate) {
        List<FunctionScheduled> result = new ArrayList<FunctionScheduled>();
        for (Room room : rooms) {
            result.addAll(FunctionScheduled.createForEntireDay(room, movie, startDate, endDate));
        }
        return result;
    }
}
