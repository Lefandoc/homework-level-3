package ru.gb.homework7;

public class Dog {
    private final String name;
    private int age;

    public Dog() {
        name = "Shark";
    }

    @BeforeSuite
    public void awaking() {
        System.out.println(name + " awakened");
    }

    @Test(priority = 5)
    public void voice() {
        System.out.println("Woof");
    }

    @Test(priority = 8)
    public void voice2() {
        System.out.println("Bow-wow");
    }

    @Test(priority = 3)
    public void voice3() {
        System.out.println("Bark bark");
    }


    @AfterSuite
    public void sleep() {
        System.out.println(name + " going to sleep");
    }

    @Override
    public String toString() {
        return name;
    }
}
