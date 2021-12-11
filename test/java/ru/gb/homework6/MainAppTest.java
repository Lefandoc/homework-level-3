package ru.gb.homework6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {

    private MainApp app;

    @BeforeEach
    public void init() {
        app = new MainApp();
    }

    @ParameterizedTest
    @MethodSource("dataForArrayFrom4")
    void testArrayFrom4(Integer[] input, Integer[] output) {
        Assertions.assertArrayEquals(output, app.arrayFrom4(input));
    }

    public static Stream<Arguments> dataForArrayFrom4() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new Integer[]{1, 2, 3, 4, 5, 6}, new Integer[]{5, 6}));
        out.add(Arguments.arguments(new Integer[]{4, 5, 6, 7, 8, 9, 4}, new Integer[]{}));
        out.add(Arguments.arguments(new Integer[]{4, 5, 6, 7, 8, 9, 4, 1, 2, 3}, new Integer[]{1, 2, 3}));
        out.add(Arguments.arguments(new Integer[]{6, 5, 4, 3, 2, 1, 4, -3, -2, -1, 0}, new Integer[]{-3, -2, -1, 0}));
        return out.stream();
    }

    @Test
    void testArrayFrom4ExceptionResult() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                app.arrayFrom4(new Integer[]{1, 2, 3}));
        assertEquals("No 4 found in array", exception.getMessage());
    }

    @Test
    void testArrayFrom4ExceptionResultEmptyArray() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                app.arrayFrom4(new Integer[]{}));
        assertEquals("No 4 found in array", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("dataForIsHere1and4")
    void testIsHere1and4(Integer[] input, boolean result) {
        Assertions.assertEquals(result, app.isHere1and4(input));
    }

    public static Stream<Arguments> dataForIsHere1and4() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new Integer[]{2, 2}, false));
        out.add(Arguments.arguments(new Integer[]{1, 4}, true));
        out.add(Arguments.arguments(new Integer[]{1, 1, 4, 1, 4}, true));
        out.add(Arguments.arguments(new Integer[]{}, false));
        out.add(Arguments.arguments(new Integer[]{1, 1, 1}, false));
        out.add(Arguments.arguments(new Integer[]{4, 4, 4}, false));
        return out.stream();
    }
}