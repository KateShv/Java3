package lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainClass {
    static final int MIN_PRIORITY = 1;
    static final int MAX_PRIORITY = 10;

    public static void main(String[] args) {
        start(TestClass.class);
        start(Sample2.class);
    }

    public static void start(Class clazz) {
        checkBAS(clazz);       //check number of Before or After Suites in class, trow RuntimeException if number != 1
        int tests = 0;
        int passed = 0;
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //top priority start
        for (int i = MAX_PRIORITY; i >= MIN_PRIORITY; i--) {
            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Test.class)) {
                    if (m.getAnnotation(Test.class).priority() == i) {
                        tests++;
                        try {
                            m.invoke(null);
                            passed++;
                        } catch (InvocationTargetException wrappedExc) {
                            Throwable exc = wrappedExc.getCause();
                            System.out.println(m + " failed: " + exc);
                        } catch (IllegalAccessException exc) {
                            System.out.println("Invalid @Test: " + m);
                        }
                    }
                }
                // Processing repeatable annotations
                if (m.isAnnotationPresent(ExceptionTest.class)
                        || m.isAnnotationPresent(ExceptionTestContainer.class)) {
                    tests++;
                    try {
                        m.invoke(null);
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (Throwable wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        int oldPassed = passed;
                        ExceptionTest[] excTests =
                                m.getAnnotationsByType(ExceptionTest.class);
                        for (ExceptionTest excTest : excTests) {
                            if (excTest.value().isInstance(exc)) {
                                passed++;
                                break;
                            }
                        }
                        if (passed == oldPassed)
                            System.out.printf("Test %s failed: %s %n", m, exc);
                    }
                }
            }
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(AfterSuite.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n",
                passed, tests - passed);
    }

    private static void checkBAS(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        int before = 0;
        int after = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) before++;
            if (m.isAnnotationPresent(AfterSuite.class)) after++;
        }
        if (after != 1 || before != 1) throw new RuntimeException();
    }

}
