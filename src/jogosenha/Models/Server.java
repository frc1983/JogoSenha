package jogosenha.Models;

import Enumerators.GameColors;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    HashMap<Integer, ArrayList<GameColors>> passwordsActiveGames;//PID do usuario, Senha do jogo
    HashMap<Integer, ArrayList<LeaderBoards>> leaderboard;//Placar geral do servidor

    public Server() {
        this.passwordsActiveGames = new HashMap<>();
        this.leaderboard = new HashMap<>();
    }
}
