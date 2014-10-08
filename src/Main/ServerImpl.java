package Main;

import Interfaces.ServerInterface;
import Enumerators.GameColors;
import Enumerators.ResultColors;
import Models.Game;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements ServerInterface {

    private static final long serialVersionUID = 234L;
    static private Integer nextPID = 1;

    protected ServerImpl() throws RemoteException {
        
    }

    @Override
    public int getPID(String name) throws RemoteException {
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
    public boolean getGame(int pid) throws RemoteException {
        //Criar uma instancia de jogo para o pid enviado
        //Gerar uma senha para este jogo e coloca-lo na lista de <PId, Senha> do server
        //Retorna true para jogo criado e false para erro ao criar o jogo
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ResultColors> attempt(int pid, ArrayList<GameColors> attempt) {
        //Busca a resposta para o jogo do PID na lista de <PId, Senha> do server
        //Compara a tentativa recebida com a resposta
        //Retorna as cores de acordo com os acertos da tentativa
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLeaderBoard() {
        //retorna a lista de leaderboards do servidor (nome e pontos ou nro de tentativas)
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
