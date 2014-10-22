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

public class Client {

    

    public static void main(String[] args) throws RemoteException, NotBoundException, Exception {
        try {
            Game game = new Game();
            game.ShowMainMenu();
        } catch (Exception e) {
            Helpers.ShowMessage.showMessage("client", e.getMessage());
        }
    }

    
}
