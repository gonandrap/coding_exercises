package java_exercises.oop.codingDesign.onlineReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Annotator {
    String bookName;
    Map<String, List<Annotation>> annotations;          // since value is not page-indexed, removing them in the future will be O(n)

    public Annotator() {
        annotations = new HashMap<String, List<Annotation>>();
    }

    public void createNote(String bookName, int page, String note) {
        List<Annotation> bookAnnotations = new ArrayList<Annotation>();
        if (annotations.containsKey(bookName) == false) {   
            annotations.put(bookName, bookAnnotations);
        }
        getBookAnnotation(bookName).add(new NoteAnnotation(bookName, page, note));
    }

    public void highlight(String bookName, int page, String highlight) {
        getBookAnnotation(bookName).add(new HighlightAnnotation(bookName, page, highlight));
    }

    public void displayAnnotations(String bookName) {
        System.out.println(String.format("Displaying annotation for book %s", bookName));
        for (Annotation a : getBookAnnotation(bookName)) {
            a.display();
        }
    }

    private List<Annotation> getBookAnnotation(String bookName) {
        List<Annotation> bookAnnotations = annotations.get(bookName);
        if (bookAnnotations == null) {   
            bookAnnotations = new ArrayList<Annotation>();
            annotations.put(bookName, bookAnnotations);
        } 
        return bookAnnotations;
    }
}
