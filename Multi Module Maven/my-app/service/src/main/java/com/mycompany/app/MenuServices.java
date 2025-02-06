package com.mycompany.app;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;

public class MenuServices {
    public boolean verifyMenu(String option) {
        Pattern pattern = Pattern.compile("^[1-6x]$");
        Matcher matcher;
        boolean matchFound;
        matcher = pattern.matcher(option);
        matchFound = matcher.find();
        if (!matchFound) {
            System.out.print("Invalid input, input a value from 1 to 6 or lowercase x: ");
            return false;
        }
        return true;
    }

    public void printMenu() {
        System.out.println("MENU: ");
        System.out.println("[1] - Search");
        System.out.println("[2] - Edit");
        System.out.println("[3] - Print");
        System.out.println("[4] - Reset");
        System.out.println("[5] - Sort");
        System.out.println("[6] - Add New Column");
        System.out.println("[x] - Exit");
        System.out.print("Action: ");
    }

    public String getMenu(Scanner s) {
        String option;
        do {
            option = s.nextLine();
            option = option.trim();
        } while (!verifyMenu(option));
        return option;
    }

    public int menuInput(String option) {
        if (option.equals("x")) {
            System.out.println(option);
            return 7;
        } else {
            return Integer.parseInt(option);
        }
    }

    public ArrayList<ArrayList<Pair>> initializeArray(String[] args, Scanner s, Random rand, PairServices pairServices) {
        int[] dimensions;
        ArrayList<ArrayList<Pair>> array = null;
        if (args.length > 0) {
            array = FileUtil.readFromFile(s, rand, args[0]);
        }
   
        if (array == null) {
        dimensions = pairServices.reset(s);
        array = pairServices.generate(dimensions[0], dimensions[1], rand);
        }
        return array;
    }

    public void singleMenuAction(int option, ArrayList<ArrayList<Pair>> array, Scanner s, Random rand, PairServices pairServices) {
        int[] dimensions;
        switch (option) {
            case 1:
                pairServices.printSearchResults(pairServices.search(array, pairServices.getSearch(s)));
                break;
            case 2:
                pairServices.printEditResults(pairServices.edit(array, pairServices.getEdit(array, s), s));
                break;
            case 3:
                pairServices.printArray(array);
                break;
            case 4:
                dimensions = pairServices.reset(s);
                array = pairServices.generate(dimensions[0], dimensions[1], rand);
                break;
            case 5:
                pairServices.sort(array, pairServices.getSort(s));
                break;
            case 6:
                pairServices.addNewColumn(array);
                break;
            case 7:
                System.out.println("Exiting...");
                break;
        }
    }

    public void menuMainLoop(ArrayList<ArrayList<Pair>> array, Scanner s, Random rand, PairServices pairServices) {
        int option;
        do {
            printMenu();
            option = menuInput(getMenu(s));
            singleMenuAction(option, array, s, rand, pairServices);
        } while (option != 7);
    }

    public void closingApp(String[] args, ArrayList<ArrayList<Pair>> array, Scanner s) {
        if (args.length > 0) {
            FileUtil.writeToFile(array, args[0]);
        } else {
            System.out.print("Input file name to save the data to or leave blank to discard: ");
            String fileName = s.nextLine();
            if (!fileName.isEmpty()) {
                FileUtil.writeToFile(array, fileName);
            }
        }
    }
}
