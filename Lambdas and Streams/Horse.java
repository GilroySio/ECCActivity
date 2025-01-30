import java.util.Optional;

public class Horse {
    private int id;
    private String name;
    private Optional<String> warCry;
    private int age;
    private boolean isHealthy;
    private int trackLeft;
    private int raceClass;
    private long raceDuration;
    public Horse(String name, String warCry, int age, boolean isHealthy, int trackLeft) {
        this.name = name;
        this.warCry = Optional.ofNullable(warCry);
        this.age = age;
        this.isHealthy = isHealthy;
        this.trackLeft = trackLeft;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarCry(String warCry) {
        this.warCry = Optional.of(warCry);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setIsHealthy(boolean isHealthy) {
        this.isHealthy = isHealthy;
    }

    public void setTrackLeft(int trackLeft) {
        this.trackLeft = trackLeft;
    }

    public void setRaceClass(int raceClass) {
        this.raceClass = raceClass;
    }

    public void setRaceDuration(long raceDuration) {
        this.raceDuration = raceDuration;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Optional<String> getWarCry() {
        return this.warCry;
    }

    public int getAge() {
        return this.age;
    }

    public boolean getIsHealthy() {
        return this.isHealthy;
    }

    public int getTrackLeft() {
        return this.trackLeft;
    }

    public int getRaceClass() {
        return this.raceClass;
    }

    public int getRaceDuration() {
        return (int) this.raceDuration;
    }
}