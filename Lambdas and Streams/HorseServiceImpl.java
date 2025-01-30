import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.time.LocalTime;

public class HorseServiceImpl implements HorseService {

    private Random rand = new Random();
    public ArrayList<Horse> initHorses(int count, int raceTrackLength) {
        ArrayList<Horse> h = new ArrayList<Horse>();
        HorseFactory hf = new HorseFactory();
        for(int i = 0; i < count; i++) {
            h.add(hf.createRandomHorse(i+1, raceTrackLength));
            //h.add(new Horse(horseNames.get(rand.nextInt(40)), warCries.get(rand.nextInt(40)), rand.nextInt(2, 20), rand.nextBoolean(), raceTrackLength));
        }
        return h;
    }

    public ArrayList<Horse> filterHealthyHorses(ArrayList<Horse> horses) {
        return new ArrayList<Horse>(horses.stream().filter(horse -> horse.getIsHealthy()).toList());
    }

    public float getAverageAge(ArrayList<Horse> horses) {
        return (float) horses.stream().mapToInt(i -> i.getAge()).sum() / horses.size();
    }

    public int getCommonAge(ArrayList<Horse> horses) {
        Map<Integer, Long> hashMap = horses.stream().collect(Collectors.groupingBy(h -> h.getAge(), Collectors.counting()));
        //System.out.println(hashMap);
        int max = hashMap.entrySet().stream().map(h -> h.getValue().intValue()).max(Comparator.comparingInt(a -> a)).orElse(0);
        hashMap = hashMap.entrySet().stream().filter(h -> h.getValue().intValue() == max).collect(Collectors.toMap(h -> h.getKey(), h-> h.getValue()));
        int commonMax = hashMap.entrySet().stream().map(h -> h.getKey()).max(Comparator.comparingInt(a -> a)).orElse(0);
        //System.out.println(hashMap);
        return commonMax;
    }

    public void setHorseClass(ArrayList<Horse> horses) {
        float avgAge = getAverageAge(horses);
        int commonAge = getCommonAge(horses);
        horses.stream().forEach(h -> {
            if (h.getAge() == commonAge) {
                h.setRaceClass(3);
            } else if (h.getAge() >= avgAge) {
                h.setRaceClass(2);
            } else {
                h.setRaceClass(1);
            }
        });
    }

    public void printHorseInfo(Horse horse) {
        System.out.println("Horse "+horse.getId()+" information");
        System.out.println("Name: "+horse.getName());
        if (horse.getWarCry().isPresent()) {
            System.out.println("Warcry: "+horse.getWarCry().get());
        } else {
            System.out.println("Warcry: None");
        }
        System.out.println("Age: "+horse.getAge());
        switch (horse.getRaceClass()) {
            case 1:
                System.out.println("Class: Beginner");
                break;
            case 2:
                System.out.println("Class: Intermediate");
                break;
            case 3:
                System.out.println("Class: Advanced");
                break;
        }
        System.out.println();
    }

    public void printHorsesInfo(ArrayList<Horse> horses) {
        System.out.println(horses.size()+ " qualified horses");
        horses.stream().forEach(h -> printHorseInfo(h));

    }

    public void moveHorse(Horse horse, int turn) {
        int distance;
        if (horse.getRaceClass() == 3 && turn >= 3) {
            distance = rand.nextInt(5, 11)*10;
        } else if (horse.getRaceClass() == 2 && turn >= 5) {
            distance = rand.nextInt(1, 11)*11;
        } else {
            distance = rand.nextInt(1, 11)*10;
        }

        horse.setTrackLeft(horse.getTrackLeft() - distance);
        
        float realDistance = (float) distance/10;
        float realDistanceLeft =  horse.getTrackLeft() < 0 ? 0 : (float) horse.getTrackLeft()/10;
        if (realDistanceLeft <= 0) {
            String warCry = horse.getWarCry().isPresent() ? horse.getWarCry().get() : "No warcry";
            System.out.println(LocalTime.now() + " " + horse.getName() + " " + horse.getId() + " ran " + realDistance + " finishing the race." + warCry);
            //System.out.println(horse.getWarCry().isPresent() ? horse.getWarCry().get() : "No warcry");
        } else {
            System.out.println(LocalTime.now() + " " + horse.getName() + " " + horse.getId() + " ran " + realDistance + " remaining " + realDistanceLeft);
        }
    }

    public void race(ArrayList<Horse> horses) {
        System.out.println("Race starting in 3, 2, 1... ");
        long startTime = System.currentTimeMillis();
        ArrayList<Horse> finishedHorses = new ArrayList<Horse>();
        horses.parallelStream().forEach(h -> {
            int turn = 0;
            while(h.getTrackLeft() > 0) {
                turn++;
                moveHorse(h, turn);
            }
            h.setRaceDuration(System.currentTimeMillis() - startTime);
            synchronized (finishedHorses) {
                finishedHorses.add(h);
            }
        });

        System.out.println(finishedHorses.getFirst().getName() + " won the race.");
        System.out.println(finishedHorses.getFirst().getWarCry().isPresent() ? finishedHorses.getFirst().getWarCry().get() : "No warcry");
        int counter = 0;
        for (Horse h: finishedHorses) {
            counter++;
            System.out.println("#" + counter + " - " + h.getName() + " " + h.getId() + " - " + h.getRaceDuration() + "ms");
        }
    }
}
