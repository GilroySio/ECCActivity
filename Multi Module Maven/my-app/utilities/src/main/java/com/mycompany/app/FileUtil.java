package com.mycompany.app;

//import com.mycompany.app.pair.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileUtil {
    public static void writeToFile(ArrayList<ArrayList<Pair>> array, String fileName) {
        try {
            File f = new File(fileName);
            if (f.createNewFile()) {
                System.out.println("File created...");
            } else {
                System.out.println("Overwriting file...");
            }
            FileUtils.write(f, "", "UTF-8");
            for (ArrayList<Pair> i : array) {
                for (Pair j : i) {
                    if (j != null) {
                        FileUtils.write(f, j.getKeyValue() + " ", "UTF-8", true);
                    }
                }
                FileUtils.write(f, "\n", "UTF-8", true);
            }
            
        } catch(IOException e) {
            System.out.println("Error while writing to file.");
        }
    }

    public static ArrayList<ArrayList<Pair>> readFromFile(Scanner s, Random rand, String fileName) {
        try {
            ArrayList<ArrayList<Pair>> p = new ArrayList<ArrayList<Pair>>();
            String[] cellValues;
            String[] keyValueArray;
            int counter = 0;

            File f = new File(fileName);
            List<String> lines = FileUtils.readLines(f, "UTF-8");
            for (String line : lines) {
                cellValues = line.split(" ");
                p.add(new ArrayList<Pair>());
                for (String i: cellValues) {
                    i = i.trim();
                    keyValueArray = i.split(",");
                    p.get(counter).add(new Pair(keyValueArray[0], keyValueArray[1]));
                }
                counter++;
            }
            return p;
        } catch(IOException e) {
            System.out.println("Cannot read from file, creating new array.");
            return null;
        }
    }
}
