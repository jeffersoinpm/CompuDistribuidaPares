/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author danie
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    private Integer portNumber;
    
    public MultiServerThread(Socket socket, Integer portNumber) {
        super("KKMultiServerThread");
        this.socket = socket;
        this.portNumber = portNumber;
    }
    
    public void run() {

        try (
            BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter outClient = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String hostName = socket.getInetAddress().getHostAddress();
            Conexion obj = new Conexion();
            ArrayList<Base> datosBase = new ArrayList<>();
            datosBase = obj.obtenerDatos();
            int portNumServer = datosBase.get(datosBase.size() - 1).getPuertoservidor() + 1;
            System.out.println("puerto al que me voy a conectar cuando se me conecten" + portNumServer);
            Thread nextClient = new Thread(new Cliente(hostName, portNumServer));
            nextClient.start();

            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                if (inputLine.equals("Bye.")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}