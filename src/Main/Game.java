package Main;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import Interfaces.ServerInterface;
import Models.Player;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

    private Player player;
    private HashMap<Boolean, ArrayList<ResultColors>> result;
    private ServerInterface p = null;
    private int attemptNumber;

    private void InitPlayer(Scanner s) throws RemoteException {
        Helpers.ShowMessage.showMessage("client", "Insira seu nome.");
        String name = s.nextLine();
        player = new Player(p.GetPID(), name);
    }
    
    private void InitGame(ServerInterface p) throws RemoteException {
        result = new HashMap<>();
        Boolean done;
        Scanner s = new Scanner(System.in);

        try {
            if (player == null) {
                InitPlayer(s);
            }
            
            ShowCreatedGame();

            do {
                done = TryColors(getColorsForAttempt(s));

                if (!done) {
                    Helpers.ShowMessage.showMessage("client", "Errou, tente novamente!");
                } else {
                    Helpers.ShowMessage.showMessage("client", "Acertou, Parabéns!");
                    p.SetLeaderboard(player.getPid(), player.getName(), attemptNumber);
                    ShowMainMenu();
                }
            } while (!done);
        } catch (Exception e) {
            DestroyGame(p);
        }
    } 
    
    private void DestroyGame(ServerInterface p) throws RemoteException {
        if (player != null) {
            p.KillGame(player.getPid());
        }
    }

    private boolean TryColors(ArrayList<GameColors> selectedColors) throws RemoteException {
        //Salva tentativa do usuário
        player.attempt.add(selectedColors);
        //Enviar tentativa
        Helpers.ShowMessage.showMessage("client", "Enviando tentativa");
        result = p.Attempt(player.getPid(), selectedColors);
        boolean done = (Boolean) result.keySet().toArray()[0];

        attemptNumber++;
        Helpers.ShowMessage.showMessage("client", "Tentativa número " + attemptNumber);
        ShowResults(result.get(done));

        return done;
    }

    private void ShowResults(ArrayList<ResultColors> result) {
        for (ResultColors color : result) {
            Helpers.ShowMessage.showMessage("client", color.name());
        }
    }    

    private int ReadOption(Scanner s) {
        ShowOptions();

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int number = -1;
        while (!validInput) {
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                if (number < 0 || number > 6) {
                    throw new NumberFormatException();
                }
                validInput = true;
            } catch (NumberFormatException e) {
                Helpers.ShowMessage.showMessage("client", "Número inválido!");
                validInput = false;
            }
        }

        return number;
    }

    private void ShowMenuOptions() {
        System.out.println("-------------------Opções do Jogo---------------------");
        System.out.println("1 para JOGAR");
        System.out.println("2 para VER RANKING");
        System.out.println("3 para HACK GAME");
        System.out.println("0 para SAIR");        
    }
    
    private void ShowOptions() {
        System.out.println("-------------------Opções de Cores---------------------");
        System.out.println("0 para AZUL");
        System.out.println("1 para VERMELHO");
        System.out.println("2 para AMARELO");
        System.out.println("3 para VERDE");
        System.out.println("4 para ROXO");
        System.out.println("5 para ROSA");
        System.out.println("6 para SAIR DO JOGO");
    }
    
    public void ShowMainMenu() throws NotBoundException, MalformedURLException, RemoteException {
        boolean menuSelected = false;
        attemptNumber = 0;
        if (p == null) {
            p = (ServerInterface) Naming.lookup("//localhost/PID");
        }

        try {
            do {
                Scanner s = new Scanner(System.in);
                ShowMenuOptions();
                int option = ReadMenuOption(s);
                switch (option) {
                    case 0:
                        if (p != null)
                            DestroyGame(p);
                        
                        System.exit(0);
                        break;
                    case 1:
                        InitGame(p);
                        menuSelected = true;
                        break;
                    case 2:
                        Helpers.ShowMessage.showMessage("client", p.GetLeaderBoard());
                        break;
                    case 3:
                        InitHackGame(p);
                        menuSelected = true;
                        break;
                }
            } while (!menuSelected);
        } catch (Exception e) {
            Helpers.ShowMessage.showMessage("client", e.getMessage());
        }
    }

    private int ReadMenuOption(Scanner s) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int number = -1;
        while (!validInput) {
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                if (number < 0 || number > 3) {
                    throw new NumberFormatException();
                }
                validInput = true;
            } catch (NumberFormatException e) {
                Helpers.ShowMessage.showMessage("client", "Menu inválido!");
                validInput = false;
            }
        }

        return number;
    }

    private ArrayList<GameColors> getColorsForAttempt(Scanner s) {
        //Exibir menu com opções                
        ArrayList<GameColors> selectedColors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int option;
            option = ReadOption(s);
            if (option == 6) {
                System.exit(0);
            }

            selectedColors.add(GameColors.getColor(option));
        }

        return selectedColors;
    }

    private void ShowCreatedGame() throws RemoteException, Exception {
        if (p.GetGame(player.getPid())) {
            Helpers.ShowMessage.showMessage("client", "Jogo criado para = "
                    + player.getName() + " PID: " + player.getPid());
            Helpers.ShowMessage.showMessage("client", "Senha gerada.");
            Helpers.ShowMessage.showMessage("client", "Tente descobrir!");
        } else {
            throw new Exception("Não foi possível criar o jogo.");
        }
    }

    private void InitHackPlayer(Scanner s) throws RemoteException {
        Helpers.ShowMessage.showMessage("client", "Insira seu nome.");
        String name = s.nextLine();
        Helpers.ShowMessage.showMessage("client", "Insira seu PID.");
        String pid = s.nextLine();
        player = new Player(pid, name);
    }
    
    private void InitHackGame(ServerInterface p) throws RemoteException {
        result = new HashMap<>();
        Boolean done;
        Scanner s = new Scanner(System.in);

        try {
            if (player == null) {
                InitHackPlayer(s);
            }
            
            p.InsertGame(player.getPid(), getColorsForAttempt(s));
            Helpers.ShowMessage.showMessage("client", "Envie as tentativas!");
            do {
                done = TryColors(getColorsForAttempt(s));

                if (!done) {
                    Helpers.ShowMessage.showMessage("client", "Errou, tente novamente!");
                } else {
                    Helpers.ShowMessage.showMessage("client", "Acertou, Parabéns!");
                    p.SetLeaderboard(player.getPid(), player.getName(), attemptNumber);
                    ShowMainMenu();
                }
            } while (!done);
        } catch (Exception e) {
            DestroyGame(p);
        }
    }
}
