package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MenuServicesTest {

    MenuServices ms;
    ArrayList<ArrayList<Pair>> testArray;

    //@Mock
    //PairServices mockPairServices;

    @BeforeEach
    void setUp() {
        ms = new MenuServices();
        
        testArray = new ArrayList<ArrayList<Pair>>();
        testArray.add(new ArrayList<Pair>());
        testArray.get(0).add(new Pair("key", "val"));
        testArray.get(0).add(new Pair("eee", "asd"));
        testArray.add(new ArrayList<Pair>());
        testArray.get(1).add(new Pair("123", "456"));
        testArray.get(1).add(new Pair("krb", "o12"));
    }

    @Test
    public void verifyMenuTest() {
        assertTrue(ms.verifyMenu("1"));
        assertTrue(ms.verifyMenu("2"));
        assertTrue(ms.verifyMenu("3"));
        assertTrue(ms.verifyMenu("4"));
        assertTrue(ms.verifyMenu("5"));
        assertTrue(ms.verifyMenu("6"));
        assertTrue(ms.verifyMenu("x"));
        assertFalse(ms.verifyMenu(""));
        assertFalse(ms.verifyMenu("a"));
        assertFalse(ms.verifyMenu("k"));
        assertFalse(ms.verifyMenu("7"));
    }

    @Test
    public void singleMenuActionTest() {
        ArrayList<ArrayList<Integer>> sampleData = new ArrayList<>();
        sampleData.add(new ArrayList<>(List.of(1, 2, 3)));

        PairServices mockPairServices = mock(PairServices.class);
        when(mockPairServices.getSearch(any(Scanner.class))).thenReturn("string");
        when(mockPairServices.search(any(ArrayList.class), any(String.class))).thenReturn(sampleData);
        
        ms.singleMenuAction(1, testArray, new Scanner(System.in), null, mockPairServices);

        verify(mockPairServices).getSearch(any(Scanner.class));
        verify(mockPairServices).search(any(ArrayList.class), any(String.class));
    }
}