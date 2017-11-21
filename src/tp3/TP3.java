/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p805924
 */
public class TP3 {

    public static ConcurrentLinkedQueue<String> taskSack = new ConcurrentLinkedQueue<>();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 6969;
        ServerSocket server = new ServerSocket(port);

        new Thread(() -> {
            while (true) {
                try {
                    taskSack.add("popopo: " + Math.random());
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TP3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

        while (true) {
            Socket client = server.accept();
            new Thread(new Execucao(client)).start();
        }
    }

    public static class Execucao implements Runnable {

        Socket client;
        ObjectOutputStream out;
        ObjectInputStream in;

        public Execucao(Socket client) throws IOException {
            this.client = client;

            out = new ObjectOutputStream(this.client.getOutputStream());
            in = new ObjectInputStream(this.client.getInputStream());

        }

        @Override
        public void run() {
            while (true) {
                try {

                    String req = (String) in.readObject();
                    switch (req) {
                        case "solicitar":
                            String task = takeTask();
                            if (task == null) {
                                task = "vazio";
                                out.writeObject(task);
                                continue;
                            }
                            System.out.println("Enviando tarefa");
                            out.writeObject(task);
                            break;
                        case "responder":
                            System.out.println("Aguardando resposta");
                            String resp = (String) in.readObject();
                            System.out.println("Processando resposta");
                            getResponse(resp);
                            break;
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(TP3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static synchronized String takeTask() {
        return taskSack.poll();
    }

    public static synchronized void putTask(String task) {
        taskSack.add(task);
    }

    public static synchronized void getResponse(String response) {
        System.out.println(response);
    }

}