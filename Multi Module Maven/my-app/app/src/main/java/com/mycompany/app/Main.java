package com.mycompany.app;

//import com.mycompany.app.pair.shaded.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        PairServices pairServices = new PairServices();
        MenuServices menuServices = new MenuServices();
        Random rand = new Random();
        ArrayList<ArrayList<Pair>> array;

        array = menuServices.initializeArray(args, s, rand, pairServices);
        menuServices.menuMainLoop(array, s, rand, pairServices);
        menuServices.closingApp(args, array, s);
    }
}