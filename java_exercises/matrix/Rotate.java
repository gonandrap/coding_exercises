import java.util.Arrays;
import java.util.Random;

public class Rotate {
    int[][] matrix;

    int numberRows;
    int numberCols;

    public Rotate(int numberRows, int numberCols) {
        assert(numberRows == numberCols);
        this.numberRows = numberRows;
        this.numberCols = numberCols;

        matrix = new int[numberRows][numberCols];
        Random rand = new Random(123123213);

        for (int i=0; i < numberRows; i++) {
            for (int j=0; j < numberCols; j++) {
                matrix[i][j] = rand.nextInt(10)+1;      // random numbers between 1 and 10
            }
        }
    }

    public void rotate() {
        /*
         * [1,9,3,0]          [4,8,5,1]
         * [5,2,6,8]      ->  [9,5,2,9]
         * [8,5,2,7]          [0,2,6,3]
         * [4,9,0,3]          [3,7,8,0]
         * 
         * 
         * Solution : the solution from "cracking the coding interview" is overly complicated! I don't even understand it. (kind of). Isn't better to just iterate the matrix once reading per column (inverse order) and write per row (in order). 
         *      The challenge though is to not mess up the indexes. It took me a while to realize that it is better / easier to just have 4 indexes : readingI, readingJ, writingI and writingJ that are updated independantly. 
         */
        
        int readingI, writingI, readingJ, writingJ;

        int[][] result = new int[numberRows][numberCols];
        
        writingI = 0;
        for (readingJ = 0; readingJ < numberCols; readingJ++) {
            writingJ = 0;
            for (readingI = numberRows-1; readingI >= 0; readingI--) {
                result[writingI][writingJ] = matrix[readingI][readingJ];
                writingJ++;
            }
            writingI++;
        }

        matrix = result;            // sucks! O(n^2). Can be improved to O(1) space if we do the rotation in place, for that, we need to do the rotations all at once (that is how the book is doing it). With my implementation, the best I could potentially get is O(n) for space. I won't try though.
    }

    public void print() {
        for(int i = 0; i < numberRows; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }


    static public void main(String[] args) {
        Rotate rotate = new Rotate(5,5);
        rotate.print();
        System.out.println("");
        rotate.rotate();
        rotate.rotate();
        rotate.rotate();
        rotate.rotate();
        rotate.print();             // should print the same as original
    }
}
