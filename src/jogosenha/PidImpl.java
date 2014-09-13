/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogosenha;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author 11180315
 */
public class PidImpl extends UnicastRemoteObject implements PidInterface{
   private static final long serialVersionUID = 234L;
   static private Integer nextPID = 1;
   
   
   
   protected PidImpl() throws RemoteException{
   }
   
   @Override 
   public int getPID() throws RemoteException{
       int pid;
       System.out.println("PidSever > Entrada");
       
       pid = nextPID;
       try{
           Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
       ++nextPID;
       System.out.println("PidServer > Saída");
       return pid;
 }
}
