package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;

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
        assertEquals(3, list.size());
        assertEquals(4, list.get(0).size());
    }

    @Test
    public void resetTest() {
        mockInput = new ByteArrayInputStream("3x4\nabc\n5x6".getBytes());
        scanner = new Scanner(mockInput);
        int[] expectedResult;
        
        expectedResult = new int[] {3, 4};
        assertArrayEquals(expectedResult, ps.reset(scanner));

        expectedResult = new int[] {5, 6};
        assertArrayEquals(expectedResult, ps.reset(scanner));
    }

    @Test
    public void getSearchTest() {
        mockInput = new ByteArrayInputStream("search".getBytes());
        scanner = new Scanner(mockInput);
        String expectedResult = "search";
        assertEquals(expectedResult, ps.getSearch(scanner));
    }

    @Test
    public void searchTest() {
        ArrayList<ArrayList<Integer>> expectedResult;

        testArray.get(0).add(null);

        expectedResult = new ArrayList<ArrayList<Integer>>();
        expectedResult.add(new ArrayList<Integer>(List.of(1, 0, 0)));
        assertEquals(expectedResult, ps.search(testArray, "key"));

        expectedResult = new ArrayList<ArrayList<Integer>>();
        expectedResult.add(new ArrayList<Integer>(List.of(1, 0, 0)));
        expectedResult.add(new ArrayList<Integer>(List.of(3, 0, 1)));
        assertEquals(expectedResult, ps.search(testArray, "e"));

        expectedResult = new ArrayList<ArrayList<Integer>>();
        assertEquals(expectedResult, ps.search(testArray, "ztx"));

        expectedResult = new ArrayList<ArrayList<Integer>>();
        assertEquals(expectedResult, ps.search(testArray, ""));

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
    public void getEditTest() {
        mockInput = new ByteArrayInputStream("1x0-k\nabc\n0x0-b".getBytes());
        scanner = new Scanner(mockInput);
        String expectedResult;

        expectedResult = "1x0-k";
        assertEquals(expectedResult, ps.getEdit(testArray, scanner));

        expectedResult = "0x0-b";
        assertEquals(expectedResult, ps.getEdit(testArray, scanner));
    }

    @Test
    public void editTest() {
        mockInput = new ByteArrayInputStream("try\npat\nsam\nple\nnul\nval".getBytes());
        scanner = new Scanner(mockInput);

        testArray.get(0).add(null);

        String[] expectedResult;

        expectedResult = new String[]{testArray.get(0).get(0).getKey(), "try"};
        assertArrayEquals(expectedResult, ps.edit(testArray, "0x0-k", scanner));
        assertEquals("try", testArray.get(0).get(0).getKey());

        expectedResult = new String[]{testArray.get(0).get(1).getValue(), "pat"};
        assertArrayEquals(expectedResult, ps.edit(testArray, "0x1-v", scanner));
        assertEquals("pat", testArray.get(0).get(1).getValue());

        expectedResult = new String[]{testArray.get(1).get(0).getKeyValue(), "sam,ple"};
        assertArrayEquals(expectedResult, ps.edit(testArray, "1x0-b", scanner));
        assertEquals("sam,ple", testArray.get(1).get(0).getKeyValue());

        expectedResult = new String[]{"null", "nul,val"};
        assertArrayEquals(expectedResult, ps.edit(testArray, "0x2-k", scanner));
        assertEquals("nul,val", testArray.get(0).get(2).getKeyValue());
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
    public void getSortTest() {
        mockInput = new ByteArrayInputStream("a\nr\nd".getBytes());
        scanner = new Scanner(mockInput);
        String expectedResult;

        expectedResult = "a";
        assertEquals(expectedResult, ps.getSort(scanner));

        expectedResult = "d";
        assertEquals(expectedResult, ps.getSort(scanner));
    }

    @Test
    public void sortTest() {
        Pair p1, p2;
        testArray.get(0).add(null);
        testArray.get(1).add(0, null);

        ps.sort(testArray, "a");
        for (ArrayList<Pair> row : testArray) {
            for (int i = 0; i < row.size() - 1; i++) {
                p1 = row.get(i);
                p2 = row.get(i+1);
                if(p2 != null){
                    assertTrue(p1.getKeyValue().compareTo(p2.getKeyValue()) <= 0);
                }
            }
        }

        ps.sort(testArray, "d");
        for (ArrayList<Pair> row : testArray) {
            for (int i = 0; i < row.size() - 1; i++) {
                p1 = row.get(i);
                p2 = row.get(i+1);
                if(p2 != null){
                    assertTrue(p1.getKeyValue().compareTo(p2.getKeyValue()) >= 0);
                }
            }
        }
    }

    @Test
    public void addNewColumnTest() {
        ps.addNewColumn(testArray);
        assertEquals(3, testArray.get(0).size());
        ps.addNewColumn(testArray);
        assertEquals(4, testArray.get(0).size());
    }

    
}
