/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogosenha;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author 11180315
 */
public class PidCliente {
    public static void main(String[] args) throws RemoteException, NotBoundException{
        try{
            PidInterface p = (PidInterface) Naming.lookup("//localHost/PID");
            System.out.println("PID =" +p.getPID());
        }catch (MalformedURLException e){
            e.printStackTrace();
            
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (NotBoundException e){
            e.getStackTrace();
        }
            }
}
