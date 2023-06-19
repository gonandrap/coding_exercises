package java_exercises.oop.codingDesign.onlineReader;

public class HighlightAnnotation extends Annotation{
    
    String bookName;
    int page;
    String content;

    public HighlightAnnotation(String bookName, int page, String content) {
        this.bookName = bookName;
        this.page = page;
        this.content = content;
    }

    @Override
    public void display() {
        System.out.println(String.format("\tHighlight of book %s in page %d is = %s ", bookName, page, content));
    }
}
