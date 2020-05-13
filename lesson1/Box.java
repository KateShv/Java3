package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {

    private ArrayList<T> box;

    public Box(T... fruits) {
        this.box = new ArrayList<>(Arrays.asList(fruits));
    }

    public void addFruit (T fruit) {
        box.add(fruit);
    }

    public float getWeight() {
        if (box.size() == 0) return 0.0f;
        return box.size() * box.get(0).getWeight();
    }

    public boolean compare (Box<?> box) {
        if (box == null) return false;
        return Math.abs(this.getWeight() - box.getWeight()) < 0.001f;
    }

    public void transfer (Box<? super T> box2) {
        if (box2 == null) return;
        if (!this.equals(box2)) {
            box2.box.addAll(this.box);
        }
        this.box.clear();
    }

}
