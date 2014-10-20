package Main;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import Interfaces.ServerInterface;
import Models.Player;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {

    private static Player player;
    private static HashMap<Boolean, ArrayList<ResultColors>> result;

    public static void main(String[] args) throws RemoteException, NotBoundException, Exception {
        ServerInterface p = null;
        result = new HashMap<>();
        Boolean done;
        int attemptNumber = 0;
        String playerPID = null;
        
        try {
            p = (ServerInterface) Naming.lookup("//localhost/PID");
            
            Helpers.ShowMessage.showMessage("client", p.getLeaderBoard());

            //Solicitar PID, monta o player sem jogo
            player = new Player(p.getPID(), getPlayerName(args));
            playerPID = player.getPid();
            //Solicitar jogo
            if (p.getGame(playerPID)) {
                Helpers.ShowMessage.showMessage("client", "Jogo criado para = " + player.getPid());
            } else {
                throw new Exception("Não foi possível criar o jogo.");
            }

            do {
                //Exibir menu com opções
                Scanner s = new Scanner(System.in);
                ArrayList<GameColors> selectedColors = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    int option;
                    do {
                        option = readOption(s);
                    } while (option < 0 || option > 6);

                    selectedColors.add(GameColors.getColor(s.nextInt()));
                }

                //Salva tentativa do usuário
                player.attempt.add(selectedColors);
                //Enviar tentativa
                Helpers.ShowMessage.showMessage("client", "Enviando tentativa");
                result = p.attempt(playerPID, selectedColors);
                done = (Boolean) result.keySet().toArray()[0];
                
                attemptNumber++;
                Helpers.ShowMessage.showMessage("client", "Tentativa número " + attemptNumber);
                showResults(result.get(done));
                if (!done) {
                    Helpers.ShowMessage.showMessage("client", "Errou, tente novamente!");
                } else {
                    Helpers.ShowMessage.showMessage("client", "Acertou, Parabéns!");
                    p.setLeaderboard(playerPID, player.getName(), attemptNumber);
                    p.killGame(playerPID);
                }

            } while (!done);
        } catch (Exception e) {
            p.killGame(playerPID);
        }
    }

    private static void showResults(ArrayList<ResultColors> result) {
        for (ResultColors color : result) {
            Helpers.ShowMessage.showMessage("client", color.name());
        }
    }

    private static String getPlayerName(String[] args) {
        //TODO: Pegar nome
        return null;
    }

    private static void ShowOptions() {
        //TODO: Criar menu de opções
        System.out.println("-------------------Opções do Jogo---------------------");
        System.out.println("0 para AZUL");
        System.out.println("1 para VERMELHO");
        System.out.println("2 para AMARELO");
        System.out.println("3 para VERDE");
        System.out.println("4 para ROXO");
        System.out.println("5 para ROSA");
    }

    private static int readOption(Scanner s) {
        ShowOptions();
        do {
            s = new Scanner(System.in);
        } while (!s.hasNextInt());

        return s.nextInt();
    }
}
