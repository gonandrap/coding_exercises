
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Implementation note about performance
 *  I'm not calculating the compressed result length upfront, so I'm creating an empty StringBuilder that will resize when it needs,
 *  so every resizing will be costly!
 * Performance improvement : figure out the length of the result (linear pass), create the builder with that size and start adding 
 * chars -> no resizes will be made!
 */
public class Compressor {
    public String compress(String s) {
        
        StringBuilder builder = new StringBuilder();
        int j = 0;
        for(int i = 0; i < s.length(); i = j) {
            j = i + 1;
            while ( j < s.length() && s.charAt(i) == s.charAt(j)) {
                j++;
            }
            if (j-i > 1) {
                builder.append(s.charAt(i));
                builder.append(j-i);
            } else {
                builder.append(s.charAt(i));
            }        
        }

        return builder.toString();
    }
    
    static public void main(String[] args) {
        Compressor compressor = new Compressor();
        String input = "aaabbaavvvvvvnmks";
        System.out.println(String.format("Original string = %s", input));
        System.out.println(String.format("Result = %s", compressor.compress(input)));

        String input2 = "gonzalito";
        System.out.println(String.format("Original string = %s", input2));
        System.out.println(String.format("Result = %s", compressor.compress(input2)));

    }
}
