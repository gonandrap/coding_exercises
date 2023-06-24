
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.swing.MenuSelectionManager;

public class MeetingsSlots {
    int minAllocationSlot =  50;
    
    public List<int[]> findFreeSlots(int[][] calendar1, int calendar1Size, int[][] calendar2, int calendar2Size, int meetingDuration)  {
        List<int[]> result = new ArrayList<int[]>();
        /*
         * Example:
         *      calendar1 = [[900, 1100], [1100,1200], [1300,1600]]
         *      calendar2 = [[800, 1000], [1200,1300], [1400,1700]]
         *      meeting time = 100          // 1 hour
         * 
         *      result = [[900,1000], [1400,1500], [1500,1600]]
         * 
         * Here the slots I receive as input are the free ones, not the busy ones!
         */

        int i, j;
        for(i = j = 0; i<calendar1Size && j<calendar2Size;) {
            // if there is no intersection, lets continue
            if (calendar1[i][0] >= calendar2[j][1]) {
                j++;
            } else if (calendar1[i][1] <= calendar2[j][0]) {
                i++;
            } else {
                // I know there is an interseccion, let's find out how many meeting slots we can accomodate over there
                int minStart = Integer.max(calendar1[i][0], calendar2[j][0]);
                int maxEnd = Integer.min(calendar1[i][1], calendar2[j][1]);
                result.addAll(findMeetingTimes(minStart, maxEnd, meetingDuration));

                if (j < calendar2Size-1 && calendar1[i][1] > calendar2[j+1][0]) {
                    j++;
                } else if (i < calendar1Size-1 && calendar1[i+1][0] > calendar2[j][1]) {
                    i++;
                } else {
                    i++;
                    j++;
                }
            }
        }

        return result;
    }

    public List<int[]> findFreeSlotsFromOccupiedOnes(int[][] calendar1, int[] calendar1Boundaries, int calendar1Size, int[][] calendar2, int calendar2Size, int[] calendar2Boundaries, int meetingDuration)  {
        /*
         * This version is a little bit more complex, since I receive the busy slots. I have to figure out where the free ones are, before starting to look
         * for intersections.
         * In addition, I receive two extra parameters : the boundaries of each calendar
         * NOTE : To simplify the nomenclature, I use ints and half and hour is represented by 50, instead of 30. Few examples:
         *  12:30 -> 1250
         *  12:00 -> 1200
         * If I use 30 instead of 50, I can't use decimal calculations because in one military hour (100) I would have 3 half hours instead of 2
         * 
         * Example:
         *      calendar1 = [[900, 1130], [1130,1200], [1300,1330]]
         *      calendar2 = [[800, 1000], [1200,1300], [1500,1630]]
         *      meeting time = 100          // 1 hour
         * 
         *      result = [[900,1000], [1400,1500], [1500,1600]]
         * 
         *  Two approaches:
         *      1. find the free slots and proceed like in the first version
         *      2. work directly with the busy slots : play around with min,max and boundaries for each interval
         *          It adds complexity the fact that you have to know about the end of the previous slot and the beginning of the next one. Special case for the first one (doesnt have a previous slot) and last one (doesnt have next slot)
         *              For the first and last case, we can insert "artificial" slots created based on the boundaries.
         *              UPDATE : too complicated! too many ifs
         * 
         */
        List<int[]> freeSlots1 = findFreeSlotsForCalendar(calendar1, calendar1Boundaries, calendar1Size);
        List<int[]> freeSlots2 = findFreeSlotsForCalendar(calendar2, calendar2Boundaries, calendar2Size);

        return findFreeSlots(listToArray(freeSlots1), freeSlots1.size(), listToArray(freeSlots2), freeSlots2.size(), meetingDuration);
    }

    private int[][] listToArray(List<int[]> list) {
        int[][] result = new int[list.size()][2];

        for(int i=0; i<list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private List<int[]> findFreeSlotsForCalendar(int[][] calendar, int[] calendarBoundaries, int calendarSize) {
        List<int[]> result = new ArrayList<int[]>();
        
        int i, calendarCurrentMin, calendarCurrentMax;
        for(i = 0; i<calendarSize; i++) {
            if (i == 0) {
                calendarCurrentMin = calendarBoundaries[0];
                calendarCurrentMax = calendar[i][0];
            } else {
                calendarCurrentMin = calendar[i-1][1];
                calendarCurrentMax = calendar[i][0];
            }
            int[] slot = {calendarCurrentMin, calendarCurrentMax};
            result.add(slot);
        }

        // let's get the latest free slot (from the latest busy hour to the day upper boundary)
        calendarCurrentMin = calendar[i-1][1];      // latest ending busy time
        calendarCurrentMax = calendarBoundaries[1];
        int[] slot = {calendarCurrentMin, calendarCurrentMax};
        result.add(slot);

        return result;
    }


    private List<int[]> findMeetingTimes(int startTime, int endTime, int meetingDuration) {
        List<int[]> result = new ArrayList<int[]>();
        while (startTime + meetingDuration <= endTime) {
            int[] slot = {startTime,startTime+meetingDuration};
            result.add(slot);
            startTime += minAllocationSlot;
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] calendar1 = {{900,1100}, {1100,1200}, {1300,1800}};
        int[][] calendar2 = {{800,1000}, {1200,1300}, {1400,1700}};
        MeetingsSlots slots = new MeetingsSlots();

        List<int[]> result = slots.findFreeSlots(calendar1, 3, calendar2, 3, 100);
        printSlots("Free slots (free slots as input)", result);

        int[][] busyCalendar1 = {{900,1150}, {1150,1200}, {1300,1350}};
        int[] boundariesBusyCalendar1 = {800, 2000};
        int[][] busyCalendar2 = {{800,1000}, {1200,1300}, {1500,1650}};
        int[] boundariesBusyCalendar2 = {700, 1900};

        List<int[]> result2 = slots.findFreeSlotsFromOccupiedOnes(busyCalendar1, boundariesBusyCalendar1, 3, busyCalendar2, 3, boundariesBusyCalendar2, 100);
        printSlots("Free slots (busy slots as input)", result2);
    }

    public static void printSlots(String label, List<int[]> slots) {
        StringBuilder builder = new StringBuilder(slots.size());
        for(var slot : slots) {
            builder.append(String.format("[%d,%d] ", slot[0], slot[1]));
        }
        System.out.println(String.format("%s -> %s", label, builder.toString()));
    } 
}
