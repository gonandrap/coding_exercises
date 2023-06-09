
public class SingletonExample {
    private static SingletonExample _instance;

    public static SingletonExample get(String whoAmI) {              // Need to make it synchronized
        synchronized(SingletonExample.class) {
            if (_instance == null) {
                System.out.println(whoAmI + "->Creating object");
                _instance = new SingletonExample();
            } else {
                System.out.println(whoAmI + "->Already created");
            }
        }
        return _instance;
    }

    public static void main(String[] args) {
        final int numberThreads = 100;
        ThreadExample[] threads = new ThreadExample[numberThreads];
        for (int i = 0; i < numberThreads; i++) {
            threads[i] = new ThreadExample("thread" + String.valueOf(i));    
        }

        for (int i = 0; i < numberThreads; i++) {
            threads[i].start();
        }
    }
}

class ThreadExample extends Thread {
    public ThreadExample(String aName) {
        setName(aName);
    }

    @Override
    public void run() {
        SingletonExample.get(getName());
    }
}

