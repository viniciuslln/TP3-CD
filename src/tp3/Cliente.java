/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tp3.TP3.takeTask;

/**
 *
 * @author p805924
 */
public class Cliente {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String serverAddr = args.length > 1 ? args[0] : "localhost";
        int serverPort = args.length > 2 ? Integer.parseInt(args[1]) : 6969;

        Socket server = new Socket(serverAddr, serverPort);
        ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());
        while (true) {
            try {
                
                out.writeObject("solicitar");
                
                System.out.println("Receber tarefa...");
                String tarefa = (String) in.readObject();
                if(tarefa.equals("vazio")){
                    System.out.println("Não há tarefas disponíveis, aguardando 3s para nova requisição.");
                    Thread.sleep(3000);
                    continue;
                }
                
                System.out.println("Processar tarefa...");
                String req = receberTarefa(tarefa);
                
                out.writeObject("responder");

                System.out.println("Enviar resposta...");
                out.writeObject(processarTarefa(req));

            } catch (ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String receberTarefa(String req) {
        return req;
    }

    public static String processarTarefa(String tarefa) {
        return "gogogo: "+ tarefa;
    }

}
