package com.betsesportivas.Runnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class RunnableClient implements Runnable {
    private Socket socket;
    private int idGrupo;

    public RunnableClient(Socket socket, int idGrupo) {
        this.socket = socket;
        this.idGrupo = idGrupo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(idGrupo);
                outputStream.flush();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                List<ContadorGrupo> resposta1 = new LinkedList<ContadorGrupo>();
                System.out.println(inputStream.readObject());
                resposta1.add((ContadorGrupo) inputStream.readObject());
                List<LogGrupo> resposta2 = new LinkedList<LogGrupo>();
                resposta2.add((LogGrupo) inputStream.readObject());
                System.out.println(resposta1.toString());
                System.out.println(resposta2.toString());
                inputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}