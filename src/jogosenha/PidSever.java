/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogosenha;
import java.rmi.Naming;
import java.rmi.RemoteException;
/**
 *
 * @author 11180315
 */
public class PidSever {
    public static void main(String[] args){
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
           }catch(RemoteException e){
               System.out.println("PID registry already running.");
           }
        try{
            Naming.rebind("PID", new PidImpl());
            System.out.println("PidServer is ready");
            }catch (Exception e){
                System.out.println("Pid Failed:");
                e.printStackTrace();
            }
    }
}
