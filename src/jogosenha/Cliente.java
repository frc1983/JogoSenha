package jogosenha;

import Enumerators.GameColors;
import Interfaces.ServerInterface;
import Models.Player;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import jdk.net.Sockets;

public class Cliente {

    private static Player player;

    public static void main(String[] args) throws RemoteException, NotBoundException, Exception {
        try {
            ServerInterface p = (ServerInterface) Naming.lookup("//localhost/PID");
            System.out.println("PID = " + p.getPID());

            //Solicitar PID, monta o player sem jogo
            player = new Player(p.getPID(), getPlayerName(args));

            //Solicitar jogo
            if (p.getGame(player.getPid())) {
                System.out.println("Jogo criado para = " + p.getPID());
            } else {
                throw new Exception("Não foi possível criar o jogo.");
            }

            //Exibir menu com opções
            Scanner s = new Scanner(System.in);
            ArrayList<GameColors> selectedColors = new ArrayList<GameColors>();
            for (int i = 0; i < 4; i++) {
                int option;
                do {
                    option = getOption(s); 
                } while(option < 0 || option > 6);      
                
                selectedColors.add(GameColors.getColor(s.nextInt()));
            }
            //Salva tentativa do usuário
            player.attempt.add(selectedColors);

            //Enviar tentativa
            System.out.println("Enviando tentativa");
            p.attempt(player.getPid(), selectedColors);
            
        } catch (MalformedURLException | RemoteException e) {
        } catch (NotBoundException e) {
            e.getStackTrace();
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
    
    private static int getOption(Scanner s){
        ShowOptions();
        do {
            s = new Scanner(System.in);
        } while (!s.hasNextInt());
        
        return s.nextInt();
    }
}
