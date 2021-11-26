package ru.gb.homework1;

import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        MainApp app = new MainApp();

        Integer[] array1 = {1, 2, 3};
        String[] array2 = {"a", "b", "c"};
        Boolean[] array3 = {false, false, true};
        app.swapElements(array1, 1, 2);
        app.swapElements(array2, 1, 2);
        app.swapElements(array3, 1, 2);
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println(Arrays.toString(array3));

        ArrayList<Integer> integers = app.arrToList(array1);
        ArrayList<String> strings = app.arrToList(array2);
        ArrayList<Boolean> booleans = app.arrToList(array3);

        Box<Apple> box = new Box<>();
        box.putFruit(new Apple());
        box.putFruit(new Apple());
        box.putFruit(new Apple());
        System.out.println(box);
        System.out.println(box.getWeight());
        Box<Orange> box2 = new Box<>();
        box2.putFruit(new Orange());
        box2.putFruit(new Orange());
        System.out.println(box2);
        System.out.println(box2.getWeight());

        System.out.println(box.compare(box2));
        Box<Apple> box3 = new Box<>();
        box.shiftFruits(box3);
        System.out.println(box);
        System.out.println(box3);

    }

    //    1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public <T> void swapElements(T[] arr, int first, int second) {
        T temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    //    2. Написать метод, который преобразует массив в ArrayList;
    public <T> ArrayList<T> arrToList(T[] arr) {
        return new ArrayList<T>(Arrays.asList(arr));
    }
}
