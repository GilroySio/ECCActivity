package org.example;


import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static int[] reset(Scanner s){
        String dimensions;
        Pattern pattern = Pattern.compile("^[0-9]+x[0-9]+$");
        Matcher matcher;
        boolean match_found;
        boolean valid_input = false;
        String[] dimensions_array;
        int[] dimensions_array_int = {0, 0};
        System.out.print("Input table dimension: ");
        do{
            dimensions = s.nextLine();
            dimensions = dimensions.trim();
            matcher = pattern.matcher(dimensions);
            match_found = matcher.find();
            if(!match_found){
                System.out.print("Incorrect format, input in the format [number]x[number]: ");
                continue;
            }
            dimensions_array = dimensions.split("x");
            dimensions_array_int = new int[]{Integer.parseInt(dimensions_array[0]), Integer.parseInt(dimensions_array[1])};
            if(dimensions_array_int[0] == 0 || dimensions_array_int[1] == 0){
                System.out.print("Invalid input, dimension cannot be 0: ");
                valid_input = false;
            }else{
                dimensions_array_int = new int[]{Integer.parseInt(dimensions_array[0]), Integer.parseInt(dimensions_array[1])};
                valid_input = true;
            }
        }while(!match_found || !valid_input);
        return dimensions_array_int;
    }
    public static String[][] generate(int len, int width, Random rand){
        String[][] array = new String[len][width];
        int random_int;
        String str;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < width; j++){
                str = "";
                for(int k = 0; k < 3; k++){
                    random_int = rand.nextInt(33, 126);
                    str += ((char) random_int);
                }
                array[i][j] = str;
                //array[i][j] = String.valueOf(i+2*j);
            }
        }
        print_array(array);
        return array;
    }
    public static void print_array(String[][] array){
        for (String[] i : array){
            for (String j : i){
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
    public static void search(String[][] array, Scanner s){
        String target;
        System.out.print("Search: ");
        target = s.nextLine();
        target = target.trim();
        boolean occurred = false;
        int counter;
        if(!target.isEmpty()){
            for(int i = 0; i < array.length; i++){
                for(int j = 0; j < array[i].length; j++){
                    counter = 0;
                    for(int k = 0; k <= array[i][j].length()-target.length(); k++) {
                        if(array[i][j].substring(k, k+target.length()).equals(target)){
                            counter++;
                        }
                    }
                    if(counter > 0){
                        System.out.println(counter + " Occurrence/s at [" + i + "," + j + "]");
                        occurred = true;
                    }
                }
            }

        }
        if(!occurred){
            System.out.println("No occurrences");
        }
    }
    public static void edit(String[][] array, Scanner s){
        String index;
        Pattern pattern = Pattern.compile("^[0-9]+x[0-9]+$");
        Matcher matcher;
        boolean match_found;
        boolean valid_input = false;
        String[] index_array;
        int[] index_array_int = new int[2];
        int[] dimensions_array_int = {array.length, array[0].length};
        System.out.print("Edit: ");
        do{
            index = s.nextLine();
            index = index.trim();
            matcher = pattern.matcher(index);
            match_found = matcher.find();
            if(!match_found){
                System.out.print("Incorrect format, input the index in the format [number]x[number]: ");
                continue;
            }
            index_array = index.split("x");
            index_array_int = new int[]{Integer.parseInt(index_array[0]), Integer.parseInt(index_array[1])};
            if(index_array_int[0] >= dimensions_array_int[0] || index_array_int[1] >= dimensions_array_int[1]){
                System.out.print("Invalid value, index out of bounds: ");
                valid_input = false;
            }else{
                index_array = index.split("x");
                index_array_int = new int[]{Integer.parseInt(index_array[0]), Integer.parseInt(index_array[1])};
                valid_input = true;
            }
        }while(!match_found || !valid_input);
        System.out.print("Value: ");
        String value = s.nextLine();
        value = value.trim();
        System.out.println(array[index_array_int[0]][index_array_int[1]] + " -> " + value);
        array[index_array_int[0]][index_array_int[1]] = value;
    }
    public static int menu(Scanner s){
        String option;
        Pattern pattern = Pattern.compile("^[1-4x]$");
        Matcher matcher;
        boolean match_found;

        System.out.println("MENU: ");
        System.out.println("[1] - Search");
        System.out.println("[2] - Edit");
        System.out.println("[3] - Print");
        System.out.println("[4] - Reset");
        System.out.println("[x] - Exit");
        System.out.print("Action: ");
        option = s.nextLine();
        option = option.trim();
        do{
            matcher = pattern.matcher(option);
            match_found = matcher.find();
            if (!match_found){
                System.out.print("Invalid input, input a value from 1 to 4 or lowercase x: ");
                option = s.nextLine();
                option = option.trim();
            }
        }while(!match_found);
        if (option.equals("x")){
            return 5;
        }else{
            return Integer.parseInt(option);
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random rand = new Random();
        int[] dimensions;
        String[][] array;
        int option;
        dimensions = reset(s);
        array = generate(dimensions[0], dimensions[1], rand);
        do{
            option = menu(s);
            switch(option){
                case 1:
                    search(array, s);
                    break;
                case 2:
                    edit(array, s);
                    break;
                case 3:
                    print_array(array);
                    break;
                case 4:
                    dimensions = reset(s);
                    array = generate(dimensions[0], dimensions[1], rand);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
            }
        }while(option != 5);
    }
}