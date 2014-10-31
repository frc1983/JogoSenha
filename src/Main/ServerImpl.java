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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public boolean InsertGame(String pid, ArrayList<GameColors> password) throws RemoteException {
        //Gerar uma senha para este jogo e coloca-lo na lista de <PId, Senha> do server
        this.passwordsActiveGames.put(pid, password);

        Helpers.ShowMessage.showMessage("server", "Jogo criado para = " + pid);
        //Retorna true para jogo criado e false para erro ao criar o jogo
        return !this.passwordsActiveGames.get(pid).isEmpty();
    }

    @Override
    public void KillGame(String pid) throws RemoteException {
        if (this.passwordsActiveGames.containsKey(pid)) {
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

        this.leaderboard.put("1", new LeaderBoards("Fabio", 4));
        this.leaderboard.put("2", new LeaderBoards("Tiao", 2));
        this.leaderboard.put("3", new LeaderBoards("Ze", 3));
        this.leaderboard.put("4", new LeaderBoards("Lezinho", 2));
        this.leaderboard.put("5", new LeaderBoards("Abestado", 9));
        this.leaderboard.put("6", new LeaderBoards("Igordão", 12));
        this.leaderboard.put("7", new LeaderBoards("Ivana", 1));

        Map<String, LeaderBoards> sorted = sortByLeaders(this.leaderboard);

        result.append("------------HALL DA FAMA------------").append("\n\n");
        if (this.leaderboard.size() <= 0) {
            result.append("Nenhum jogador encontrado.").append("\n");
        } else {
            for (Entry<String, LeaderBoards> item : sorted.entrySet()) {
                result.append("Posição ").append(position)
                        .append(" Nome: ").append(item.getValue().getName())
                        .append(" Pontos: ").append(item.getValue().getPoints())
                        .append("\n");
                position++;
                if(position == 6)
                    break;
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
        //Mostra a senha criada
        Helpers.ShowMessage.showMessage("server", "Senha gerada!");
        ShowPassword(ret);

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

    private void ShowPassword(ArrayList<GameColors> result) {
        for (GameColors color : result) {
            Helpers.ShowMessage.showMessage("server", color.name());
        }
    }

    private static HashMap sortByLeaders(HashMap map) {
        List list = new LinkedList(map.entrySet());
        
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}
