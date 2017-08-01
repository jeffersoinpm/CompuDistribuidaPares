/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jefferson
 */
public class Servidor implements Runnable {

    private Integer portNumber;

    public Servidor() {
    }

    public Servidor(Integer portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        boolean listening = true;
        try (ServerSocket serverSocket = new ServerSocket(this.portNumber)) {
            while (listening) {
                new MultiServerThread(serverSocket.accept(), portNumber).start();
	    }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
