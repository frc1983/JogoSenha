
package Models;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
    public int points;
    
    public Map<String, ArrayList<ResultColors>> attemptResult;//Resultado da tentativa  
    
    public Game(){
        points = 0;
        attemptResult = new HashMap<>();
    }
}
