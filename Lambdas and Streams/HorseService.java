import java.util.ArrayList;

public interface HorseService{
    public ArrayList<Horse> initHorses(int count, int raceTrackLength);
    public ArrayList<Horse> filterHealthyHorses(ArrayList<Horse> horses);
    public float getAverageAge(ArrayList<Horse> horses);
    public int getCommonAge(ArrayList<Horse> horses);
    public void setHorseClass(ArrayList<Horse> horses);
    public void printHorseInfo(Horse horse);
    public void printHorsesInfo(ArrayList<Horse> horses);
    public void moveHorse(Horse horse, int turn);
    public void race(ArrayList<Horse> horses);
}
