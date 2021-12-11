package ru.gb.homework4Multithreading;

import java.util.concurrent.*;

public class ABCTaskApp {

    private static final Object mon = new Object();
    private static char toPrint = 'A';

    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (toPrint != 'A') {
                                mon.wait();
                            }
                            System.out.print("A");
                            toPrint = 'B';
                            mon.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (toPrint != 'B') {
                                mon.wait();
                            }
                            System.out.print("B");
                            toPrint = 'C';
                            mon.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    try {
                        for (int i = 0; i < 5; i++) {
                            while (toPrint != 'C') {
                                mon.wait();
                            }
                            System.out.print("C\n");
                            toPrint = 'A';
                            mon.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }

}
