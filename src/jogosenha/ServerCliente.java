package jogosenha;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerCliente {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        try {
            ServerInterface p = (ServerInterface) Naming.lookup("//localHost/PID");
            System.out.println("PID =" + p.getPID());
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.getStackTrace();
        }
    }
}
