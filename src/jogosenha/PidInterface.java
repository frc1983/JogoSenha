/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogosenha;

import java.rmi.RemoteException;
import java.rmi.Remote;
/**
 *
 * @author 11180315
 */
public interface PidInterface extends Remote {
     public int getPID() throws RemoteException;
}
