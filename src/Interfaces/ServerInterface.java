package Interfaces;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import Models.Game;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;

public interface ServerInterface extends Remote {
    
    public String getPID() throws RemoteException;
    public boolean getGame(String pid) throws RemoteException;
    public HashMap<Boolean, ArrayList<ResultColors>> attempt(String pid, ArrayList<GameColors> attempt) throws RemoteException;
    public String getLeaderBoard() throws RemoteException;
}
