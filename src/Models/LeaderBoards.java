package Models;

public class LeaderBoards {
    private String name;
    private int points;
    
    public LeaderBoards(String n, int p) {
        name = n;
        points = p;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
