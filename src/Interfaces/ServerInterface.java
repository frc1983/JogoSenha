package Interfaces;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import Models.Game;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
    
    public int getPID(String name) throws RemoteException;
    public boolean getGame(int pid) throws RemoteException;
    public ArrayList<ResultColors> attempt(int pid, ArrayList<GameColors> attempt);
    public String getLeaderBoard();
}
