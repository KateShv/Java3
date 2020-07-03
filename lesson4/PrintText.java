package lesson4;

import java.util.function.Consumer;

public class PrintText implements Runnable {

    private final Controller controller;
    private final int count;
    private final String text;
    private final Consumer<String> action;

    PrintText(String text, int count, Controller controller,
              Consumer<String> action) {
        this.text = text;
        this.controller = controller;
        this.count = count;
        this.action = action;
    }

    @Override
    public void run() {
        synchronized (controller) {
            for (int i = 0; i < count; i++) {
                try {
                    while (!controller.getCurrent().equals(text)) {
                        controller.wait();
                    }
                    action.accept(text);
                    Thread.sleep(1000);
                    controller.move();
                    controller.notifyAll();
                    controller.getLatch().countDown();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}