package Models;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String pid;
    private String name;
    public ArrayList<ArrayList<GameColors>> attempt;//Lista de tentativas
    
    public Player(String id, String name){
        this.pid = id;
        this.name = name;
        attempt = new ArrayList<>();
    }

    public String getPid() {
        return pid;
    }
    
    public String getName() {
        return name;
    }
}
