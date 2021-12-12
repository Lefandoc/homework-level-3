package ru.gb.homework7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestsRunner {

    private static final String PACKAGE_NAME = "ru.gb.homework7.";

    public static void main(String[] args) {
        try {
            start(Dog.class);
            start(PACKAGE_NAME + "Dog");
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void start(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        runTests(clazz);
    }


    public static void start(String className) throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        runTests(clazz);
    }

    private static void runTests(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Тестериование класса " + clazz.getSimpleName());

        Object clazzInstance = clazz.newInstance();

        Method beforeSuite = null;
        int beforeSuiteCount = 0;
        Method afterSuite = null;
        int afterSuiteCount = 0;
        Method[] methods = clazz.getDeclaredMethods();
        final Map<Integer, ArrayList<Method>> sortedTests = new TreeMap<>(Collections.reverseOrder());

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteCount++;
                if (beforeSuiteCount > 1) {
                    throw new RuntimeException("@BeforeSuite method must be only one");
                }
                beforeSuite = m;
            }

            if (m.isAnnotationPresent(Test.class)) {
                Test annotation = m.getAnnotation(Test.class);
                int priority = annotation.priority();
                if (priority >= 1 && priority <= 10) {
                    sortedTests.put(priority, sortedTests.getOrDefault(priority, new ArrayList<>()));
                    sortedTests.get(priority).add(m);
                } else {
                    throw new RuntimeException("Can not resolve priority value " + priority +
                            " in @Test method " + m);
                }
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteCount++;
                if (afterSuiteCount > 1) {
                    throw new RuntimeException("@BeforeSuite method must be only one");
                }
                afterSuite = m;
            }
        }

        if (beforeSuite != null) {
            System.out.print("@BeforeSuite method: ");
            beforeSuite.invoke(clazzInstance);
            System.out.println();
        }

        if (!sortedTests.isEmpty()){
            System.out.println("@Test methods: ");
            for (ArrayList<Method> methodList : sortedTests.values()) {
                for (Method testMethod : methodList) {
                    testMethod.invoke(clazzInstance);
                }
            }
            System.out.println();
        }

        if (afterSuite != null) {
            System.out.print("@AfterSuite method: ");
            afterSuite.invoke(clazzInstance);
            System.out.println();
        }
    }
}
