package com.betsesportivas.Runnable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sockets.thread.ContadorGrupo;
import sockets.thread.LogGrupo;

public class RunnableClient implements Runnable {
    private Socket socket;
    private int idGrupo;
    private List<ContadorGrupo> contadorGrupos;
    private TableView<ContadorGrupo> tableView;
    private List<LogGrupo> logGrupos;
    private Label label;

    public List<ContadorGrupo> getContadorGrupos() {
        return contadorGrupos;
    }

    public List<LogGrupo> getLogGrupos() {
        return logGrupos;
    }

    public RunnableClient(Socket socket, int idGrupo, TableView<ContadorGrupo> tblViewGrupo, Label label) {
        this.socket = socket;
        this.idGrupo = idGrupo;
        this.tableView = tblViewGrupo;
        this.label = label;
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

            // IMPORTANTE:
            // envia o método rodando em uma thread separada para a thread principal do java fx
            Platform.runLater(() -> tableView.setItems(FXCollections.observableArrayList(contadorGrupos)));
            socket.close();

            while (true) {
                for (LogGrupo log : logGrupos) {

                    Platform.runLater(() -> label.setText(log.toString()));
                    Thread.sleep(2000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}