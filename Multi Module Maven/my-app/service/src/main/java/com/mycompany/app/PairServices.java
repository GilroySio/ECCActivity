package com.mycompany.app;

import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PairServices {
    public boolean validateDimensions(String dimensions) {
        Pattern pattern = Pattern.compile("^[0-9]+x[0-9]+$");

        Matcher matcher;
        boolean matchFound;

        String[] dimensionsArray;
        int[] dimensionsArrayInt = {0, 0};

        matcher = pattern.matcher(dimensions);
        matchFound = matcher.find();
        if (!matchFound) {
            System.out.print("Incorrect format, input in the format [number]x[number]: ");
            return false;
        }
        dimensionsArray = dimensions.split("x");
        dimensionsArrayInt = new int[]{Integer.parseInt(dimensionsArray[0]), Integer.parseInt(dimensionsArray[1])};
        if (dimensionsArrayInt[0] == 0 || dimensionsArrayInt[1] == 0) {
            System.out.print("Invalid input, dimension cannot be 0: ");
            return false;
        }   
        return true;
        
    }
    public int[] reset(Scanner s) {
        String dimensions;
        String[] dimensionsArray;
        int[] dimensionsArrayInt = {0, 0};
        System.out.print("Input array dimensions: ");
        do {
            dimensions = s.nextLine();
            dimensions = dimensions.trim();
        } while (!validateDimensions(dimensions));
        dimensionsArray = dimensions.split("x");
        dimensionsArrayInt = new int[]{Integer.parseInt(dimensionsArray[0]), Integer.parseInt(dimensionsArray[1])};
        return dimensionsArrayInt;
    }

    public ArrayList<ArrayList<Pair>> generate(int len, int width, Random rand) {
        ArrayList<ArrayList<Pair>> array = new ArrayList<ArrayList<Pair>>();
        int randomInt;
        String key;
        String value;

        for (int i = 0; i < len; i++) {
            array.add(new ArrayList<Pair>());
            for (int j = 0; j < width; j++) {
                key = "";
                value = "";
                for (int k = 0; k < 3; k++) {
                    randomInt = rand.nextInt(33, 126);
                    randomInt = (randomInt == 44) ? 126 : randomInt;
                    key += ((char) randomInt);

                    randomInt = rand.nextInt(33, 126);
                    randomInt = (randomInt == 44) ? 126 : randomInt;
                    value += ((char) randomInt);
                }
                array.get(i).add(new Pair(key, value));
            }
        }
        printArray(array);
        return array;
    }

    public void printArray(ArrayList<ArrayList<Pair>> array) {
        for (ArrayList<Pair> i : array) {
            for (Pair j : i) {
                if (j == null) {
                    System.out.print("Null ");
                } else {
                    System.out.print(j.getKeyValue() + " ");
                }
            }
            System.out.println();
        }
    }

    public String getSearch(Scanner s) {
        String target;
        System.out.print("Search: ");
        target = s.nextLine();
        target = target.trim();
        return target;
    }

    public ArrayList<ArrayList<Integer>> search(ArrayList<ArrayList<Pair>> array, String target) {

        ArrayList<ArrayList<Integer>> searchResults = new ArrayList<ArrayList<Integer>>();

        int counter;
        Pair p;
        if(!target.isEmpty()){
            for (int i = 0; i<array.size(); i++) {
                for (int j = 0; j<array.get(i).size(); j++) {
                    if (array.get(i).get(j) == null) {
                        continue;
                    }
                    counter = 0;
                    p = array.get(i).get(j);
                    for (int k = 0; k <= p.getKeyValue().length()-target.length(); k++) {
                        if (p.getKeyValue().substring(k, k+target.length()).equals(target)) {
                            counter++;
                        }
                    }
                    if (counter > 0) {
                        searchResults.add(new ArrayList<Integer>(List.of(counter, i, j)));
                    }
                }
            }
        }
        return searchResults;
    }

    public void printSearchResults(ArrayList<ArrayList<Integer>> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No occurrences");
        } else {
            for (ArrayList<Integer> i : searchResults) {
                System.out.println(i.get(0) + " Occurrence/s at [" + i.get(1) + "," + i.get(2) + "]");
            }
        }
    }

    public boolean validateEdit(ArrayList<ArrayList<Pair>> array, String option){
        Pattern pattern = Pattern.compile("^[0-9]+x[0-9]+-[kvb]$");
        Matcher matcher;
        boolean matchFound;

        String[] keyValueOptionArray;
        String[] indexArray;
        int[] indexArrayInt = new int[2];

        matcher = pattern.matcher(option);
        matchFound = matcher.find();
        if (!matchFound) {
            System.out.print("Incorrect format, input the index and key, value, or both in the format [number]x[number]-[k/v/b]: ");
            return false;
        }
        keyValueOptionArray = option.split("-");

        indexArray = keyValueOptionArray[0].split("x");
        indexArrayInt = new int[]{Integer.parseInt(indexArray[0]), Integer.parseInt(indexArray[1])};
        if (indexArrayInt[0] >= array.size() || indexArrayInt[1] >= array.get(indexArrayInt[0]).size()) {
            System.out.print("Invalid value, index out of bounds: ");
            return false;
        }
        return true;
    }

    public String getEdit(ArrayList<ArrayList<Pair>> array, Scanner s) {
        String option;
        System.out.print("Index [number]x[number] and key [k], value [v], or both [b] to edit: ");
        do {
            option = s.nextLine();
            option = option.trim();
        } while (!validateEdit(array, option));
        return option;
    }

    public String[] edit(ArrayList<ArrayList<Pair>> array, String option, Scanner s) {
        String[] keyValueOptionArray;
        String keyValueOption = "";

        String[] indexArray;
        int[] indexArrayInt = new int[2];

        String[] editResults = new String[2];

        keyValueOptionArray = option.split("-");
        keyValueOption = keyValueOptionArray[1];
        indexArray = keyValueOptionArray[0].split("x");
        indexArrayInt = new int[]{Integer.parseInt(indexArray[0]), Integer.parseInt(indexArray[1])};

        String key, value;
        if (array.get(indexArrayInt[0]).get(indexArrayInt[1]) == null) {
            System.out.print("Null pair detected, input key: ");
            key = s.nextLine();
            key = key.trim();
            System.out.print("Input value: ");
            value = s.nextLine();
            value = value.trim();
            array.get(indexArrayInt[0]).set(indexArrayInt[1], new Pair(key, value));
            editResults = new String[] {"null", array.get(indexArrayInt[0]).get(indexArrayInt[1]).getKeyValue()};
        } else {
            if (keyValueOption.equals("k")) {
                System.out.print("New key: ");
                key = s.nextLine();
                key = key.trim();
                editResults = new String[] {array.get(indexArrayInt[0]).get(indexArrayInt[1]).getKey(), key};
                array.get(indexArrayInt[0]).get(indexArrayInt[1]).setKey(key);
            } else if(keyValueOption.equals("v")) {
                System.out.print("New value: ");
                value = s.nextLine();
                value = value.trim();
                editResults = new String[] {array.get(indexArrayInt[0]).get(indexArrayInt[1]).getValue(), value};
                array.get(indexArrayInt[0]).get(indexArrayInt[1]).setValue(value);
            } else if(keyValueOption.equals("b")) {
                System.out.print("New key: ");
                key = s.nextLine();
                key = key.trim();
                System.out.print("New value: ");
                value = s.nextLine();
                value = value.trim();
                editResults = new String[] {array.get(indexArrayInt[0]).get(indexArrayInt[1]).getKeyValue(), key + "," + value};
                array.get(indexArrayInt[0]).set(indexArrayInt[1], new Pair(key, value));
            }
        }
        return editResults;
    }

    public void printEditResults(String[] editResults) {
        System.out.println(editResults[0] + " -> " + editResults[1]);
    }

    public boolean validateSort(String option){
        if (option.equals("a") || option.equals("d")) {
            return true;
        } else {
            System.out.print("Invalid input, input a for ascending or d for descending: ");
            return false;
        }
    }

    public String getSort(Scanner s) {
        String option;
        System.out.print("Sort in Ascending [a] or Descending order [d]: ");
        do {
            option = s.nextLine();
            option = option.trim();
        } while (!validateSort(option));
        return option;
    }

    public void sort(ArrayList<ArrayList<Pair>> array, String option) {
        for(ArrayList<Pair> i : array) {
            Collections.sort(i, new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    if (o1 == null) {
                        return 1;
                    }
                    if (o2 == null) {
                        return -1;
                    }
                    return option.equals("a") ? o1.getKeyValue().compareTo(o2.getKeyValue()) : o1.getKeyValue().compareTo(o2.getKeyValue())*-1;
                }
            });
        }
    }

    public void addNewColumn(ArrayList<ArrayList<Pair>> array) {
        for(ArrayList<Pair> i : array) {
            i.add(null);
        }
    }
}
