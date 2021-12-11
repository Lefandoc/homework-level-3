package ru.gb.homework5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    final private Race race;
    final private int speed;
    final private String name;

    private final CountDownLatch cdl;
    private static boolean isWinner = false;

    public Car(Race race, int speed, CountDownLatch cdl) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cdl = cdl;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            cdl.countDown();
            System.out.println(this.name + " готов");
            cdl.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        if (!isWinner) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победитель: " + this.getName());
            isWinner = true;
        }
    }


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
}
