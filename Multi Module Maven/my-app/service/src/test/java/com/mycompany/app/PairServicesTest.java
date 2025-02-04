package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.ArrayList;

public class PairServicesTest {

    PairServices ps;

    @BeforeEach
    void setUp(){
        ps = new PairServices();
    }

    @Test
    public void generateTest(){
        Random rand = new Random();
        ArrayList<ArrayList<Pair>> list = ps.generate(3, 4, rand);
        assertEquals(list.size(), 3);
        assertEquals(list.get(0).size(), 4);
    }



}
