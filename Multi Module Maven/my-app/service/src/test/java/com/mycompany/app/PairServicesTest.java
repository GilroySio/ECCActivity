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
import java.util.ArrayList;

public class PairServicesTest {

    PairServices ps;

    @BeforeEach
    void setUp(){
        ps = new PairServices();
    }

    @Test
    public void validateDimensionsTest(){
        assertTrue(ps.validateDimensions("3x4"));
        assertTrue(ps.validateDimensions("9x10"));
        assertFalse(ps.validateDimensions(""));
        assertFalse(ps.validateDimensions("7x0"));
        assertFalse(ps.validateDimensions("0x3"));
        assertFalse(ps.validateDimensions("3x-1"));
        assertFalse(ps.validateDimensions("abc"));
    }

    @Test
    public void generateTest(){
        Random rand = new Random();
        ArrayList<ArrayList<Pair>> list = ps.generate(3, 4, rand);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).size(), 4);
    }

    @Test
    public void resetTest(){
        ByteArrayInputStream in = new ByteArrayInputStream("3x4".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(in);
        int[] result = new int[]{3, 4};
        assertArrayEquals(ps.reset(scanner), result);
    }

}
