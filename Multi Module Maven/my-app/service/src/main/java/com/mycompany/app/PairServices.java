package com.mycompany.app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public interface PairServices {
    public boolean validateDimensions(String dimensions);
    public int[] reset(Scanner s);
    public ArrayList<ArrayList<Pair>> generate(int len, int width, Random rand);
    public void printArray(ArrayList<ArrayList<Pair>> array);
    public String getSearchInput(Scanner s);
    public ArrayList<ArrayList<Integer>> search(ArrayList<ArrayList<Pair>> array, String target);
    public void printSearchResults(ArrayList<ArrayList<Integer>> searchResults);
    public boolean validateEditInput(ArrayList<ArrayList<Pair>> array, String option);
    public String getEditInput(ArrayList<ArrayList<Pair>> array, Scanner s);
    public String[] edit(ArrayList<ArrayList<Pair>> array, String option, Scanner s);
    public void printEditResults(String[] editResults);
    public String getSortInput(Scanner s);
    public void sort(ArrayList<ArrayList<Pair>> array, String option);
    public void addNewColumn(ArrayList<ArrayList<Pair>> array);
}
