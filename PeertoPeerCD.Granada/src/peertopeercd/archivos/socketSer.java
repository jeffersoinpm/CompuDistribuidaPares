package peertopeercd.archivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class socketSer extends Thread{
    private Socket cliente;
    private PrintWriter enviar;
    private BufferedReader recibir;
    private String mensaje="";
    private ServerSocket servidor;
    public socketSer(Socket cliente,ServerSocket servidor){
        this.cliente=cliente;
        this.servidor=servidor;
    }
    private void Principal(){
        try {
            recibir=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            enviar=new PrintWriter(cliente.getOutputStream(), true);
            while((mensaje=recibir.readLine())!=null)
                System.out.println("mensaje recibido >> "+mensaje);
            
        } catch (IOException ex){
            try {
                cliente=servidor.accept();
                Principal();
            } catch (IOException ex1){
               // Logger.getLogger(socketSer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    public void run(){
        System.out.println("comenzo a ejecutarse");
        Principal();
    }
    
    
}

