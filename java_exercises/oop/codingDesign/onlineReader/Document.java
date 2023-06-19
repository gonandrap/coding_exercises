package java_exercises.oop.codingDesign.onlineReader;

import java.util.Map;
import java.util.Collections;

public class Document {
    User user;
    String name;
    Map<Integer, String> content;       // Integer : pageNumber, String : content of the page
    int numberPages;

    public Document(User user, String name, Map<Integer, String> content) {
        this.user = user;
        this.name = name;
        this.content = content;
        this.numberPages = Collections.max(content.keySet());
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getPage(int pageNumber) {
        return content.get(pageNumber);
    }

    public boolean hasNextPage(int pageNumber) {
        return ++pageNumber < numberPages;
    }

    public int numberPages() {
        return numberPages;
    }
}
