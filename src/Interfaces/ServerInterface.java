package Interfaces;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import Models.Game;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;

public interface ServerInterface extends Remote {
    
    public String GetPID() throws RemoteException;
    public boolean GetGame(String pid) throws RemoteException;
    public void KillGame(String pid) throws RemoteException;
    public HashMap<Boolean, ArrayList<ResultColors>> Attempt(String pid, ArrayList<GameColors> attempt) throws RemoteException;
    public String GetLeaderBoard() throws RemoteException;
    public void SetLeaderboard(String pid, String name, int attempts) throws RemoteException;
}
