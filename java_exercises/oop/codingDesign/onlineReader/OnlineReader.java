package java_exercises.oop.codingDesign.onlineReader;

import java.util.HashMap;
import java.util.Map;

public class OnlineReader {

    Map<String, Document> opened;               // documents uploaded
    Map<String, Integer> reading;               // documents being read
    Renderer renderer;
    Annotator annotator;

    public OnlineReader() {
        opened = new HashMap<String,Document>();
        reading = new HashMap<String,Integer>();
        renderer = new Renderer();
        annotator = new Annotator();
    }

    /*
     * Invoked when the user wants to upload a document to the online reader system
     */
    public boolean openDocument(Document document) {
        
        String bookName = document.getName();
        if (opened.containsKey(bookName) == false) {
            opened.put(bookName, document);
            System.out.println(String.format("Uploading document with name {%s} for user {%s}", document.getName(), document.getUser().getName()));
            return true;
        } else {
            System.out.println(String.format("There is another book with the name {%s}", bookName));
            return false;
        }
    }

    public String startReadString(User user, String documentName) {
        Document document = opened.get(documentName);
        if (checkUserPermissions(user, document)) {
            System.out.println(String.format("User {%s} is starting to read document {%s}", user.getName(), document.getName()));
            reading.put(documentName, 0);
            String page = document.getPage(0);
            renderer.displayContent(user.getName(), page);
            return page;
        } else {
            return null;
        }
     }

    public String nextPage(User user, String documentName) {
        Document document = opened.get(documentName);
        if (checkUserPermissions(user, document)) {
            int currentPage = reading.get(documentName);
            if (document.hasNextPage(currentPage)) {
                int nextPage = ++currentPage;
                System.out.println(String.format("User {%s} is moving to the next page [%d] of the document {%s}", user.getName(), nextPage, documentName));
                reading.put(documentName, nextPage);
                String newPage = document.getPage(nextPage);
                renderer.displayContent(user.getName(), newPage);
                return newPage;
            } else {
                System.out.println(String.format("User {%s} has finished the entire book {%s}", user.getName(), documentName));
                return null;
            }
            
        } else {
            return null;
        }
    }

    public Annotator getAnnotator() {
        return annotator;
    }

    public int currentPage(String bookName) {
        if (reading.containsKey(bookName)) {
            return reading.get(bookName);
        } else {
            System.out.println(String.format("BookName %s is not actually being read", bookName));
            return -1;
        }
    }

    private boolean checkUserPermissions(User user, Document doc) {
        if (doc == null || !doc.getUser().equals(user)) {
            System.out.println(String.format("Document {%s} is not opened or doent belong to user {%s}", doc.getName(), user.getName()));
            return false;
        } else {
            return true;
        }
    }

    public void uploadedStatus() {
        System.out.println("Printing status of opened documents");
        for(Map.Entry<String, Document> entry : opened.entrySet()) {
            System.out.println(String.format("\tBook name [%s], owner [%s]", entry.getKey(), entry.getValue().getUser().getName()));
        }
    }

    public void readingStatus() {
        System.out.println("Printing status of reading documents");
        for(Map.Entry<String, Integer> entry : reading.entrySet()) {
            System.out.println(String.format("\tBook name [%s], current page [%d]", entry.getKey(), entry.getValue()));
        }
    }


    public static void main(String[] args) {
        
        OnlineReader reader = new OnlineReader();
        User user1 = new User("Gonzalo");
        User user2 = new User("Andres");

        String bookName1 = "Cracking code interview";
        Document doc1 = new Document(user1, bookName1, createBookContent(bookName1, 123)); 

        String bookName2 = "Java interview complete guide";
        Document doc2 = new Document(user1, bookName2, createBookContent(bookName2, 59)); 

        String bookName3 = "System Design Interview";
        Document doc3 = new Document(user2, bookName3, createBookContent(bookName3, 200)); 
        
        reader.openDocument(doc3);
        reader.openDocument(doc1);
        reader.openDocument(doc2);
        
        reader.uploadedStatus();

        String content1 = reader.startReadString(user1, bookName3);
        String content2 = reader.startReadString(user2, bookName1);
        String content3 = reader.startReadString(user1, bookName1);

        reader.readingStatus();

        System.out.println(String.format("User {%s} tries to read {%s} = {%b}", user1.getName(), bookName3, content1 != null));
        System.out.println(String.format("User {%s} tries to read {%s} = {%b}", user2.getName(), bookName1, content2 != null));
        System.out.println(String.format("User {%s} tries to read {%s} = {%b}", user1.getName(), bookName1, content3 != null));

        Annotator annotationSystem = reader.getAnnotator();

        String pageContent = "";
        reader.nextPage(user1, bookName3);
        reader.nextPage(user2, bookName1);
        pageContent = reader.nextPage(user1, bookName1);
        annotationSystem.createNote(bookName1, reader.currentPage(bookName1), String.format("Creating a note for user %s", user1.getName()));
        annotationSystem.highlight(bookName1, reader.currentPage(bookName1), pageContent.substring(5, pageContent.length()));
        reader.nextPage(user1, bookName1);
        pageContent = reader.nextPage(user1, bookName1);
        annotationSystem.highlight(bookName1, reader.currentPage(bookName1), pageContent.substring(5, pageContent.length()));

        reader.readingStatus();

        annotationSystem.displayAnnotations(bookName1);
        annotationSystem.displayAnnotations(bookName2);
        annotationSystem.displayAnnotations(bookName3);
    }



    // Helper methods for testing only
    private static Map<Integer, String> createBookContent(String bookName, int numberPages) {

        Map<Integer, String> book = new HashMap<Integer, String>();
        for(int i = 0; i < numberPages; ++i) {
            book.put(i, String.format("[%s] - page %d", bookName, i));
        }
        return book;
    }

}
