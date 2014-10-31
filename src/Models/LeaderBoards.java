package Models;

public class LeaderBoards implements Comparable<LeaderBoards>{
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

    @Override
    public int compareTo(LeaderBoards o) {
        return Integer.compare(this.getPoints(), o.getPoints());
    }
}
