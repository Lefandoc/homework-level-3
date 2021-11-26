package ru.gb.homework1;

import java.util.ArrayList;

public class Box<T extends Fruit> {

    private final ArrayList<T> box;
    private Apple apple;
    private Orange orange;

    public Box() {
        box = new ArrayList<>();
    }

//    высчитывает вес коробки, зная количество фруктов и вес одного фрукта
    public float getWeight() {
        return box.size() * box.get(0).getWeight();
    }

//    метод добавления фрукта в коробку
    public void putFruit(T fruit) {
        if (fruit != null) {
            box.add(fruit);
        }
    }

    public boolean compare(Box<? extends Fruit> anotherBox) {
        return this.getWeight() == anotherBox.getWeight();
    }
// пересыпать фрукты из текущей коробки в другую коробку
    public void shiftFruits(Box<T> anotherBox) {
        ArrayList<T> newBox = anotherBox.getBox();
        newBox.addAll(box);
        box.clear();
    }

    public ArrayList<T> getBox() {
        return box;
    }

    @Override
    public String toString() {
        if (!box.isEmpty()) {
            return "Фрукты в коробке: " + box.get(0) + " x" + box.size();
        } else {
            return "В коробке пусто";
        }
    }
}
