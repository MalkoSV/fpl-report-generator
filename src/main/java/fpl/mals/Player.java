package fpl.mals;

public class Player {
    private String name;
    private int count;
    private int start;
    private int captain;
    private int score;

    public Player(String name, int count, int start, int captain, int score) {
        this.name = name;
        this.count = count;
        this.start = start;
        this.captain = captain;
        this.score = score;
    }

    public Player(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCaptain() {
        return captain;
    }

    public void setCaptain(int captain) {
        this.captain = captain;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
