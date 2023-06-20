package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

public class Room {
    String name;                // has to be unique
    int numberRows;
    int numberCols;
    int capacity;

    public Room(String name, int numberRows, int numberCols) {
        this.name = name;
        this.numberRows = numberRows;
        this.numberCols = numberCols;
        this.capacity = numberRows * numberCols;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberRows() {
        return numberRows;
    }

    public int getNumberCols() {
        return numberCols;
    }

    static public Room createRoom(String name, int numberRows, int numberCols) {
        return new Room(name, numberRows, numberCols);
    }
}
