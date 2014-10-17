package Main;

import Interfaces.ServerInterface;
import Enumerators.GameColors;
import static Enumerators.GameColors.getRandom;
import Enumerators.ResultColors;
import Models.Game;
import Models.LeaderBoards;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    HashMap<String, ArrayList<GameColors>> passwordsActiveGames;//PID do usuario, Senha do jogo
    HashMap<String, ArrayList<LeaderBoards>> leaderboard;//Placar geral do servidor
    
    protected ServerImpl() throws RemoteException {
        this.passwordsActiveGames = new HashMap<>();
        this.leaderboard = new HashMap<>();
    }

    @Override
    public String getPID() throws RemoteException {
        Helpers.ShowMessage.showMessage("server", "PidSever > Solicitacao");
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    @Override
    public boolean getGame(String pid) throws RemoteException {
        //Criar uma instancia de jogo para o pid enviado
        Game game = new Game();
        //Gerar uma senha para este jogo e coloca-lo na lista de <PId, Senha> do server
        this.passwordsActiveGames.put(pid, generatePassword());
        
        Helpers.ShowMessage.showMessage("server","Jogo criado para = " + pid);
        //Retorna true para jogo criado e false para erro ao criar o jogo
        return !this.passwordsActiveGames.get(pid).isEmpty();
    }

    @Override
    public HashMap<Boolean, ArrayList<ResultColors>> attempt(String pid, ArrayList<GameColors> attempt) throws RemoteException {
        //Busca a resposta para o jogo do PID na lista de <PId, Senha> do server
        //Compara a tentativa recebida com a resposta
        //Retorna as cores de acordo com os acertos da tentativa
        HashMap<Boolean, ArrayList<ResultColors>> result = new HashMap<>();
        result.put(Boolean.FALSE, new ArrayList<ResultColors>());
        return result;
    }

    @Override
    public String getLeaderBoard() throws RemoteException {
        //retorna a lista de leaderboards do servidor (nome e pontos ou nro de tentativas)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<GameColors> generatePassword() {
        ArrayList<GameColors> ret = new ArrayList<>(4);        
        
        for(int i = 0; i < 4; i++){
            GameColors color = getRandom();
            while(ret.contains(color)){
                color = getRandom();
            }
            ret.add(i, color);
        }
        
        return ret;
    }
}
