package java_exercises.oop.codingDesign.CinemaWithOnlineTicketing;

public class AvailabilityCounter {
    
    int numberRows;
    int numberCols;
    int total_capacity;
    int total_available;

    int[][] availability;
    
    public AvailabilityCounter(int numberRows, int numberCols) {
        total_capacity = numberRows * numberCols;
        availability = new int[numberRows][numberCols];         // TODO does it initialize to 0 each cell? ]
        this.numberRows = numberRows;
        this.numberCols = numberCols;
    }

    public boolean isAvailable(int rowNumber, int colNumber) {
        return availability[rowNumber][colNumber] == 0;
    }
    
    public void markAsOccupied(int rowNumber, int colNumber) {
        if (checkDimensions(rowNumber, colNumber)) {
            availability[rowNumber][colNumber] = 1;
        }
    }

    public void markAsFree(int rowNumber, int colNumber) {
        if (checkDimensions(rowNumber, colNumber)) {
            availability[rowNumber][colNumber] = 0;
        }
    }


    private boolean checkDimensions(int rowNumber, int colNumber) {
        return (rowNumber >= 0 && rowNumber < numberRows) &&
                (colNumber >= 0 && colNumber < numberCols);
    }
}
