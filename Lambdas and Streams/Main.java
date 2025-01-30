import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static Scanner s = new Scanner(System.in);

    public static int inputNumHorses() throws ArithmeticException {
        System.out.print("Input number of horses: ");
        int num = Integer.parseInt(s.nextLine().trim());
        if (num < 2) {
            throw new ArithmeticException("Cannot start race with less than 2 horses.");
        }
        return num;
    }

    public static int initNumHorses() {
        int num = 0;
        do {
            try {
                num = inputNumHorses();
            } catch (ArithmeticException e) {
                System.out.println("Cannot start race with less than 2 horses.");
            } catch (NumberFormatException e) {
                System.out.println("Input is not an integer.");
            }
        } while (num == 0);
        return num;
    }

    public static int inputRaceLength() throws ArithmeticException {
        System.out.print("Input race track length: ");
        int num = Integer.parseInt(s.nextLine().trim());
        if (num <= 0) {
            throw new ArithmeticException("Cannot set race track length to 0 or below.");
        }
        return num;
    }

    public static int initRaceLength() {
        int num = 0;
        do {
            try {
                num = inputRaceLength();
            } catch (ArithmeticException e) {
                System.out.println("Cannot set race track length to 0 or below.");
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number.");
            }
        } while (num == 0);
        return num;
    }

    public static void main(String[] args) {
        HorseServiceImpl hs = new HorseServiceImpl();
        ArrayList<Horse> horses;
        int raceTrackLength = initRaceLength()*10;
        do {
            horses = hs.initHorses(initNumHorses(), raceTrackLength);
            //horses.add(new Horse("Name", null, 30, true, raceTrackLength));
            horses = hs.filterHealthyHorses(horses);
            if (horses.size() < 2) {
                System.out.println("Less than two healthy horses, input number of horses again.");
            }
        } while (horses.size() < 2);
        hs.setHorseClass(horses);
        hs.printHorsesInfo(horses);
        hs.race(horses);
    }
}