import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileUtil {
    public static void writeToFile(ArrayList<ArrayList<Pair>> array, String fileName){
        try{
            File f = new File(fileName);
            if(f.createNewFile()){
                System.out.println("File created...");
            }else{
                System.out.println("Overwriting file...");
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for(ArrayList<Pair> i : array){
                for(Pair j : i){
                    if(j != null){
                        bw.write(j.getKeyValue() + " ");
                    }
                }
                bw.newLine();
            }
            bw.close();
        }catch(IOException e){
            System.out.println("Error while writing to file.");
        }

    }
    public static ArrayList<ArrayList<Pair>> readFromFile(Scanner s, Random rand, String fileName){
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            ArrayList<ArrayList<Pair>> p = new ArrayList<ArrayList<Pair>>();
            String rowValue;
            String[] cellValues;
            String[] keyValueArray;
            int counter = 0;
            while ((rowValue = br.readLine()) != null){
                cellValues = rowValue.split(" ");
                p.add(new ArrayList<Pair>());
                for(String i: cellValues){
                    i = i.trim();
                    keyValueArray = i.split(",");
                    p.get(counter).add(new Pair(keyValueArray[0], keyValueArray[1]));
                }
                counter++;
            }
            br.close();
            return p;
        }catch(IOException e){
            System.out.println("Cannot read from file, creating new array.");
            return null;
        }
    }
}
