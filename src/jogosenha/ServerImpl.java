package jogosenha;

import Enumerators.GameColors;
import Enumerators.ResultColors;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = 234L;
    static private Integer nextPID = 1;

    protected ServerImpl() throws RemoteException {
        
    }

    @Override
    public int getPID() throws RemoteException {
        int pid;
        System.out.println("PidSever > Entrada");

        pid = nextPID;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ++nextPID;
        System.out.println("PidServer > Saída");
        return pid;
    }

    @Override
    public int getGame(int pid) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ResultColors> attempt(int pid, ArrayList<GameColors> attempt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
