/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Nodo {

    private final Runnable servidor;
    private final Runnable cliente;
    private boolean run = true;

    public Nodo(Main parent) {
        servidor = new Runnable() {
            public void run() {
                byte data[] = new byte[0];
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket(VariablesGlobals.UDPPORT);
                } catch (SocketException ex) {
                    ex.printStackTrace();
                    parent.quit();
                }
                DatagramPacket packet = new DatagramPacket(data, data.length);
                while (run) {
                    try {
                        
                        socket.receive(packet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        parent.quit();
                    }
                    parent.newAddress(packet.getAddress());
                }
            }
        };
        cliente = new Runnable() {
            public void run() {
                byte data[] = new byte[0];
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket();
                } catch (SocketException ex) {
                    ex.printStackTrace();
                    parent.quit();
                }
                System.out.println("UDPPORT:"+VariablesGlobals.UDPPORT);
                System.out.println("addrestoSend"+ VariablesGlobals.addresstoSend);
                DatagramPacket packet = new DatagramPacket(
                        data, 
                        data.length, 
                        VariablesGlobals.addresstoSend, 
                        VariablesGlobals.UDPPORT);
                while (run) {
                    try {
                        socket.send(packet);
                        Thread.sleep(VariablesGlobals.UDPINTERVAL);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        parent.quit();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        parent.quit();
                    }
                }
            }
        };
        new Thread(servidor).start();
        new Thread(cliente).start();
    }

    public void quit() {
        run = false;
    }
}