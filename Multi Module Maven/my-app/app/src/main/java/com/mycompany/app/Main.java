package com.mycompany.app;

//import com.mycompany.app.pair.shaded.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static int menu(Scanner s) {
        String option;
        Pattern pattern = Pattern.compile("^[1-6x]$");
        Matcher matcher;
        boolean matchFound;

        System.out.println("MENU: ");
        System.out.println("[1] - Search");
        System.out.println("[2] - Edit");
        System.out.println("[3] - Print");
        System.out.println("[4] - Reset");
        System.out.println("[5] - Sort");
        System.out.println("[6] - Add New Column");
        System.out.println("[x] - Exit");
        System.out.print("Action: ");
        option = s.nextLine();
        option = option.trim();
        do {
            matcher = pattern.matcher(option);
            matchFound = matcher.find();
            if (!matchFound) {
                System.out.print("Invalid input, input a value from 1 to 6 or lowercase x: ");
                option = s.nextLine();
                option = option.trim();
            }
        } while (!matchFound);
        if (option.equals("x")) {
            return 7;
        } else {
            return Integer.parseInt(option);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        PairServices pairService = new PairServices();
        Random rand = new Random();
        int[] dimensions;
        ArrayList<ArrayList<Pair>> array;
        int option;

        if (args.length > 0) {
            array = FileUtil.readFromFile(s, rand, args[0]);
            if (array == null) {
                dimensions = pairService.reset(s);
                array = pairService.generate(dimensions[0], dimensions[1], rand);
            }
        } else {
            dimensions = pairService.reset(s);
            array = pairService.generate(dimensions[0], dimensions[1], rand);
        }
        do {
            option = menu(s);
            switch (option) {
                case 1:
                    pairService.printSearchResults(pairService.search(array, s));
                    break;
                case 2:
                    pairService.printEditResults(pairService.edit(array, s));
                    break;
                case 3:
                    pairService.printArray(array);
                    break;
                case 4:
                    dimensions = pairService.reset(s);
                    array = pairService.generate(dimensions[0], dimensions[1], rand);
                    break;
                case 5:
                    pairService.sort(array, s);
                    break;
                case 6:
                    pairService.addNewColumn(array);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
            }
        } while (option != 7);
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