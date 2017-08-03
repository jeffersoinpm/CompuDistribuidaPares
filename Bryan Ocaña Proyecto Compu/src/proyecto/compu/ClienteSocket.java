/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.compu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bryan
 */
public class ClienteSocket implements Runnable{
    
    private static String IP;
    private static Integer puerto;
    
    public ClienteSocket(){}
    
     public ClienteSocket(String IP, Integer puerto) {
        this.IP = IP;
        this.puerto = puerto;
    }
    
     public void run() {
         String UsuarioDestino;
        Base();

    }

    private void Base() {
        String UsuarioDestino;
        try {
            Acepta();         
        } catch (ConnectException ce) {
            System.out.println("No pudo conectarse a: " + this.IP + ":" + this.puerto);
        } catch (IOException exception) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private void Acepta() throws IOException {
        while(true) {
            System.out.println("IP:" + this.IP + " Puerto: " + this.puerto);
            Socket sc = new Socket(this.IP, this.puerto);
            ObjectOutputStream os = new ObjectOutputStream(sc.getOutputStream());
            BufferedReader brs = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            PrintWriter UserSalida = new PrintWriter(sc.getOutputStream(), true);
            System.out.println("Conectado");
            
            User(brs, UserSalida);
            os.close();
        }
    }

    private void User(BufferedReader brs, PrintWriter UserSalida) throws IOException {
        String UsuarioDestino;
        if((UsuarioDestino = brs.readLine()) != null)
        {
            System.out.println("Usuario " + UsuarioDestino);
            UserSalida.println(UsuarioDestino);
        }
    }
}
