package com.betsesportivas.Runnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sockets.thread.ContadorGrupo;
import sockets.thread.LogGrupo;

public class RunnableClient implements Runnable {
    private Socket socket;
    private int idGrupo;
    private List<ContadorGrupo> contadorGrupos;
    private ObservableList<ContadorGrupo> observableContadores;
    private List<LogGrupo> logGrupos;

    public List<ContadorGrupo> getContadorGrupos() {
        return contadorGrupos;
    }

    public List<LogGrupo> getLogGrupos() {
        return logGrupos;
    }

    public RunnableClient(Socket socket, int idGrupo, ObservableList<ContadorGrupo> contadores) {
        this.socket = socket;
        this.idGrupo = idGrupo;
        this.observableContadores = contadores;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(idGrupo);
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            contadorGrupos = (List<ContadorGrupo>) inputStream.readObject();
            logGrupos = (List<LogGrupo>) inputStream.readObject();
            inputStream.close();
            observableContadores = FXCollections.observableArrayList(contadorGrupos);
            // socket.close();

            // processarRanking(resposta1);
            // processarLogs(resposta2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

    private static void processarRanking(List<ContadorGrupo> ranking) {
        System.out.println("\n=== RANKING DOS GRUPOS ===");
        for (ContadorGrupo grupo : ranking) {
            System.out.println("ID " + grupo.getIdGrupo() +
                    " - " + grupo.getNomeGrupo() +
                    ": " + grupo.getQuantidadeUtilizacoes() + " utilizações");
        }
    }

    private static void processarLogs(List<LogGrupo> logs) {
        System.out.println("\n=== LOGS DO GRUPO ===");
        if (logs.isEmpty()) {
            System.out.println("Nenhum log encontrado para este grupo.");
        } else {
            for (LogGrupo log : logs) {
                System.out.println("Acesso em: " + log.getTimestamp());
            }
        }
    }
}