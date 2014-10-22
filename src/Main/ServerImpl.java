package Main;

import Interfaces.ServerInterface;
import Enumerators.GameColors;
import static Enumerators.GameColors.getRandom;
import Enumerators.ResultColors;
import Models.LeaderBoards;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    HashMap<String, ArrayList<GameColors>> passwordsActiveGames;//PID do usuario, Senha do jogo
    HashMap<String, LeaderBoards> leaderboard;//Placar geral do servidor

    protected ServerImpl() throws RemoteException {
        this.passwordsActiveGames = new HashMap<>();
        this.leaderboard = new HashMap<>();
    }

    @Override
    public String GetPID() throws RemoteException {
        Helpers.ShowMessage.showMessage("server", "PidSever > Solicitacao");
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    @Override
    public boolean GetGame(String pid) throws RemoteException {
        //Gerar uma senha para este jogo e coloca-lo na lista de <PId, Senha> do server
        this.passwordsActiveGames.put(pid, GeneratePassword());

        Helpers.ShowMessage.showMessage("server", "Jogo criado para = " + pid);
        //Retorna true para jogo criado e false para erro ao criar o jogo
        return !this.passwordsActiveGames.get(pid).isEmpty();
    }

    @Override
    public void KillGame(String pid) throws RemoteException {
        if(this.passwordsActiveGames.containsKey(pid)){
            this.passwordsActiveGames.remove(pid);
        }
    }

    @Override
    public HashMap<Boolean, ArrayList<ResultColors>> Attempt(String pid, ArrayList<GameColors> attempt) throws RemoteException {
        //Busca a resposta para o jogo do PID na lista de <PId, Senha> do server
        ArrayList<GameColors> password = this.passwordsActiveGames.get(pid);

        return ValidateAttempt(password, attempt);
    }

    @Override
    public void SetLeaderboard(String pid, String name, int attempts) throws RemoteException {
        Helpers.ShowMessage.showMessage("client", "Set leaderboard");
        this.leaderboard.put(pid, new LeaderBoards(name, attempts));
    }

    @Override
    public String GetLeaderBoard() throws RemoteException {
        Helpers.ShowMessage.showMessage("client", "get leaderboard");
        
        StringBuilder result = new StringBuilder();
        int position = 1;
        
        result.append("------------HALL DA FAMA------------").append("\n\n");
        if (this.leaderboard.size() <= 0) {
            result.append("Nenhum jogador encontrado.").append("\n");
        } else {
            //TODO: Ordenar pontos asc
            for (Entry<String, LeaderBoards> item : this.leaderboard.entrySet()) {
                result.append("Posição ").append(position)
                        .append(" Nome: ").append(item.getValue().getName())
                        .append(" Pontos: ").append(item.getValue().getPoints())
                        .append("\n");
                position++;
            }
        }
        //retorna a lista de leaderboards do servidor (nome e pontos ou nro de tentativas)
        return result.toString();
    }

    private ArrayList<GameColors> GeneratePassword() {
        ArrayList<GameColors> ret = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            GameColors color = getRandom();
            while (ret.contains(color)) {
                color = getRandom();
            }
            ret.add(i, color);
        }

        return ret;
    }

    private HashMap<Boolean, ArrayList<ResultColors>> ValidateAttempt(ArrayList<GameColors> password, ArrayList<GameColors> attempt) {
        //Compara a tentativa recebida com a resposta
        //Retorna as cores de acordo com os acertos da tentativa   
        HashMap<Boolean, ArrayList<ResultColors>> result = new HashMap<>();
        ArrayList<ResultColors> resultColors = new ArrayList<>();
        Boolean matchPassword = true;

        for (int i = 0; i < attempt.size(); i++) {
            for (int j = 0; j < password.size(); j++) {
                if (attempt.get(i).equals(password.get(j)) && i == j) {
                    resultColors.add(ResultColors.PRETO);
                    break;
                } else if (attempt.get(i).equals(password.get(j)) && i != j) {
                    resultColors.add(ResultColors.BRANCO);
                    break;
                }
            }
        }

        matchPassword = Collections.frequency(resultColors, ResultColors.PRETO) == 4;

        Collections.shuffle(resultColors);
        result.put(matchPassword, resultColors);

        return result;
    }

}
