
package Models;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import java.util.ArrayList;
import java.util.Map;

public class Game {
    int points;
    Map<Integer, ArrayList<GameColors>> attempt;//Lista de tentativas
    Map<Integer, ArrayList<ResultColors>> attemptResult;//Resultado da tentativa    
}
