package lesson4;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private final static List<String> list = Arrays.asList("A", "B", "C");
    private final static ExecutorService executorService = Executors.newFixedThreadPool(list.size());
    private int position = 0;
    private CountDownLatch latch;

    public Controller() {
        latch = new CountDownLatch(list.size());
    }

    public static void main(String[] args) {

        Controller controller = new Controller();

        for (String symbol : list) {
            int count = 5;
            executorService.execute(new PrintText(symbol, count, controller, System.out::print));
        }

        try {
            controller.latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    String getCurrent() {
        return list.get(position);
    }

    void move() {
        position = position == list.size() - 1 ? 0 : position + 1;
    }

}