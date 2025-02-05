package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Random;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;

public class PairServicesTest {

    PairServices ps;
    ByteArrayInputStream mockInput;
    Scanner scanner;
    ArrayList<ArrayList<Pair>> testArray;

    @BeforeEach
    void setUp() {
        ps = new PairServices();
        
        testArray = new ArrayList<ArrayList<Pair>>();
        testArray.add(new ArrayList<Pair>());
        testArray.get(0).add(new Pair("key", "val"));
        testArray.get(0).add(new Pair("eee", "asd"));
        testArray.add(new ArrayList<Pair>());
        testArray.get(1).add(new Pair("123", "456"));
        testArray.get(1).add(new Pair("krb", "o12"));
    }

    @Test
    public void validateDimensionsTest() {
        assertTrue(ps.validateDimensions("3x4"));
        assertTrue(ps.validateDimensions("9x10"));
        assertFalse(ps.validateDimensions(""));
        assertFalse(ps.validateDimensions("7x0"));
        assertFalse(ps.validateDimensions("0x3"));
        assertFalse(ps.validateDimensions("3x-1"));
        assertFalse(ps.validateDimensions("abc"));
    }

    @Test
    public void generateTest() {
        Random rand = new Random();
        ArrayList<ArrayList<Pair>> list = ps.generate(3, 4, rand);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).size(), 4);
    }

    @Test
    public void resetTest() {
        mockInput = new ByteArrayInputStream("3x4".getBytes());
        //System.setIn(mockInput);
        scanner = new Scanner(mockInput);
        int[] result = new int[]{3, 4};
        assertArrayEquals(ps.reset(scanner), result);
    }

    @Test
    public void searchTest() {
        ArrayList<ArrayList<Integer>> expectedResult;

        expectedResult = new ArrayList<ArrayList<Integer>>();
        expectedResult.add(new ArrayList<Integer>(List.of(1, 0, 0)));
        assertEquals(ps.search(testArray, "key"), expectedResult);

        expectedResult = new ArrayList<ArrayList<Integer>>();
        expectedResult.add(new ArrayList<Integer>(List.of(1, 0, 0)));
        expectedResult.add(new ArrayList<Integer>(List.of(3, 0, 1)));
        assertEquals(ps.search(testArray, "e"), expectedResult);

        expectedResult = new ArrayList<ArrayList<Integer>>();
        assertEquals(ps.search(testArray, "ztx"), expectedResult);

    }

    @Test
    public void validateEditTest() {
        assertTrue(ps.validateEdit(testArray, "1x0-k"));
        assertTrue(ps.validateEdit(testArray, "0x0-b"));
        assertTrue(ps.validateEdit(testArray, "1x1-v"));
        assertFalse(ps.validateEdit(testArray, ""));
        assertFalse(ps.validateEdit(testArray, "2x0-b"));
        assertFalse(ps.validateEdit(testArray, "abc"));
        assertFalse(ps.validateEdit(testArray, "0x0"));
        assertFalse(ps.validateEdit(testArray, "x1-k"));
        assertFalse(ps.validateEdit(testArray, "0x2-v"));   
    }

    @Test
    public void editTest() {
        mockInput = new ByteArrayInputStream("try\npat\nsam\nple".getBytes());
        scanner = new Scanner(mockInput);

        String[] expectedResult;

        expectedResult = new String[]{testArray.get(0).get(0).getKey(), "try"};
        assertArrayEquals(ps.edit(testArray, "0x0-k", scanner), expectedResult);

        expectedResult = new String[]{testArray.get(0).get(1).getValue(), "pat"};
        assertArrayEquals(ps.edit(testArray, "0x1-v", scanner), expectedResult);

        expectedResult = new String[]{testArray.get(1).get(0).getKeyValue(), "sam,ple"};
        assertArrayEquals(ps.edit(testArray, "1x0-b", scanner), expectedResult);
    }

    @Test
    public void validateSortTest() {
        assertTrue(ps.validateSort("a"));
        assertTrue(ps.validateSort("d"));
        assertFalse(ps.validateSort(""));
        assertFalse(ps.validateSort("x"));
        assertFalse(ps.validateSort("ad"));
        assertFalse(ps.validateSort("da"));
    }

    @Test
    public void addNewColumnTest() {
        ps.addNewColumn(testArray);
        assertEquals(testArray.get(0).size(), 3);
        ps.addNewColumn(testArray);
        assertEquals(testArray.get(0).size(), 4);
    }
}
