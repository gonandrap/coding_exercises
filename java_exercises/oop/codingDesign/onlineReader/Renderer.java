package java_exercises.oop.codingDesign.onlineReader;

import java.util.HashMap;
import java.util.Map;

public class Renderer {

    Map<String, String> renderingPerUser;

    public Renderer() {
        renderingPerUser = new HashMap<String, String>();
    }

    public void displayContent(String user, String content) {
        renderingPerUser.put(user, content);
        System.out.println(String.format("User %s is seeing content : %s", user, content));
    }

    public String whatIsWatching(String user) {
        String result = renderingPerUser.get(user);
        if (result == null) {
            System.out.println(String.format("User %s is not watching anything", user));
        }
        return result;
    }


}
