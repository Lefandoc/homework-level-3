package ru.gb.homework1;

public class Orange extends Fruit {

    private float weight;

    public Orange() {
        this.weight = 1.5f;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Orange";
    }
}
