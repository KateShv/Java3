package lesson7;

public class TestClass {
    @Test(priority = 7)
    static void one() {
        System.out.println("1");
    }

    @Test(priority = 1)
    static void two() {
        System.out.println("2");
    }

    @Test(priority = 5)
    static void tree() {
        throw new RuntimeException("Boom");
    }

    @Test(priority = 3)
    static void four() {
        System.out.println("4");
    }

    @Test(priority = 7)
    static void five() {
        System.out.println("5");
    }

    @BeforeSuite
    static void init() {
        System.out.println("Start testing");
    }

    @AfterSuite
    static void exit() {
        System.out.println("End of testing");
    }
}