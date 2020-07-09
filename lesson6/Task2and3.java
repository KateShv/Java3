package lesson6;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

public class Task2and3 {

    public static class Task2 {

        public static int[] elementsBehindTheFour(int[] arr) {
            for (int i = arr.length - 1; i >= 0; i--) {
                if (arr[i] == 4) {
                    return Arrays.copyOfRange(arr, i + 1, arr.length);
                }
            }
            throw new RuntimeException("Digit 4 is not found in the array");
        }

    }

    public static class Task3 {

        public static boolean findOneAndFour(int[] arrIn) {
            boolean one = false;
            boolean four = false;
            for (int i : arrIn) {
                //не совсем поняла в задании, надо чтобы массив состоял только из 1 и 4,
                //или просто наличие 1 и 4 сразу в одном массиве...
                //если только из 1 и 4, то следущая строка с доп условием нужна
                //if (i != 1 && i != 4) throw new RuntimeException("invalid value");
                if (i == 1) one = true;
                if (i == 4) four = true;
            }
            return one && four;
        }

    }

    public static void main(String[] args) {

        final int[] res2 = Task2.elementsBehindTheFour(new int[]{1, 2, 3, 4, 5, 4, 7, 8});
        System.out.println(Arrays.toString(res2));

        final boolean result = Task3.findOneAndFour(new int[]{4, 3, 1, 8, 7, 5,});
        System.out.println(result);

    }




    @RunWith(Parameterized.class)
    public static class Task2Test {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new int[]{5, 6, 7}, new int[]{1, 2, 3, 4, 5, 6, 7}},
                    {new int[]{5, 6, 7}, new int[]{1, 4, 3, 4, 5, 6, 7}},
                    {new int[]{2, 3, 6, 5, 6, 7}, new int[]{4, 2, 3, 6, 5, 6, 7}},
                    {new int[]{}, new int[]{4}},
                    {new int[]{3, 55, 77}, new int[]{321, 148, 75, 777, 4, 0, -1, 4, 3, 55, 77}}
            });
        }

        private int[] expected;
        private int[] actual;

        public Task2Test(int[] expected, int[] actual) {
            this.expected = expected;
            this.actual = actual;
        }

        @Test
        public void testElementsBehindTheFour() {
            Assert.assertArrayEquals(expected, Task2.elementsBehindTheFour(actual));
        }

    }




    @RunWith(Parameterized.class)
    public static class Task3Test {

        @Parameterized.Parameters
        public static Object[] data() {
            return new Object[]{
                    new int[]{1, 2, 3, 4, 5, 6, 7},
                    new int[]{7, 4, 3, 4, 5, 6, 1},
                    new int[]{4, 2, 3, 6, 5, 1, 7},
                    new int[]{4, 1},
                    new int[]{100, 21, 1, 777, 4, 0, -1, 4, 3, 55, 77}
            };
        }

        private int[] arr;

        public Task3Test(int[] arr) {
            this.arr = arr;
        }

        @Test
        public void testFindOneAndFour() {
            Assert.assertTrue(Task3.findOneAndFour(arr));
        }

    }



}
