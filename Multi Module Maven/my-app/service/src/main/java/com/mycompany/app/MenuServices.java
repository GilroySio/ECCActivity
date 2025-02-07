package com.mycompany.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public interface MenuServices {
    public boolean verifyMenu(String option);
    public void printMenu();
    public String getMenuInput(Scanner s);
    public int convertMenuInputToInt(String option);
    public ArrayList<ArrayList<Pair>> initializeArray(String[] args, Scanner s, Random rand, PairServicesImpl pairServices);
    public void singleMenuAction(int option, ArrayList<ArrayList<Pair>> array, Scanner s, Random rand, PairServicesImpl pairServices);
    public void menuMainLoop(ArrayList<ArrayList<Pair>> array, Scanner s, Random rand, PairServicesImpl pairServices);
    public void closingApp(String[] args, ArrayList<ArrayList<Pair>> array, Scanner s);
}
