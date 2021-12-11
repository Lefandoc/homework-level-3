package ru.gb.homework6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainApp {

    public Integer[] arrayFrom4(Integer[] arr) {
        ArrayList<Integer> list = new ArrayList<>(List.of(arr.length));
        list.clear();
        boolean found4 = false;
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] == 4) {
                found4 = true;
                for (int j = i + 1; j < arr.length; j++) {
                    list.add(arr[j]);
                }
                break;
            }
        }
        if (!found4) {
            throw new RuntimeException("No 4 found in array");
        }
        return list.toArray(new Integer[0]);
    }

    public boolean isHere1and4(Integer[] arr) {
        boolean is1 = false;
        boolean is4 = false;
        for (Integer integer : arr) {
            if (integer == 1) {
                is1 = true;
            }
            if (integer == 4) {
                is4 = true;
            }
        }
        return is1 && is4;
    }
}
