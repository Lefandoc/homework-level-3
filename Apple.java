package ru.gb.homework1;

public class Apple extends Fruit {

    private float weight;

    public Apple() {
        this.weight = 1.0f;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
