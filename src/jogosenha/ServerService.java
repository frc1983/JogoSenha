package jogosenha;

import java.rmi.Naming;
import java.rmi.RemoteException;
import jogosenha.Models.Server;

public class ServerService {

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Server server = new Server();
            
            System.out.println("RMI registry ready.");
        } catch (RemoteException e) {
            System.out.println("PID registry already running.");
        }
        try {
            Naming.rebind("PID", new ServerImpl());
            System.out.println("PidServer is ready");
        } catch (Exception e) {
            System.out.println("Pid Failed:");
            e.printStackTrace();
        }
    }
}
