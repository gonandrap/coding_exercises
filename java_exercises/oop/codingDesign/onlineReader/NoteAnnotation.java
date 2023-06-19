package java_exercises.oop.codingDesign.onlineReader;

public class NoteAnnotation extends Annotation{
    
    String bookName;
    int page;
    String noteContent;

    public NoteAnnotation(String bookName, int page, String noteContent) {
        this.bookName = bookName;
        this.page = page;
        this.noteContent = noteContent;
    }

    @Override
    public void display() {
        System.out.println(String.format("\tNote in book %s in page %d is = %s ", bookName, page, noteContent));
    }
}
