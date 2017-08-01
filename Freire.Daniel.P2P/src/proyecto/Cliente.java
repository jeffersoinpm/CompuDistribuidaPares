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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jefferson
 */
public class Cliente implements Runnable {

    private String direccionIp;
    private Integer portNumber;

    public Cliente() {
    }

    public Cliente(String direccionIp, Integer portNumber) {
        this.direccionIp = direccionIp;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        try {
            System.out.println("Ip:"+this.direccionIp+" puerto: "+this.portNumber);
            Socket kkSocket = new Socket(this.direccionIp, this.portNumber);
            PrintWriter outClient = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(kkSocket.getInputStream()));
            BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromUser;
            System.out.println("El cliente se conecto");
            while ((fromUser = stdIn.readLine()) != null) {
                    System.out.println("Client: " + fromUser);
                    outClient.println(fromUser);
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
