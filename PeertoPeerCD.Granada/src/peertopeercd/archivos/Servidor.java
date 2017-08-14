package peertopeercd.archivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread{
    private int puerto;
    public ServerSocket servidor;
    public Servidor(int puer){
        this.puerto=puer;
    }
    public void run(){
        try {
            servidor=new ServerSocket(puerto);
            for(int i=0;i<2;i++){
                Socket cliente=new Socket();
                System.out.println("Escuchandooo...  "+puerto);
                cliente=servidor.accept();
                socketSer ejecutoCliente=new socketSer(cliente,servidor);
                RecibirArchivo(puerto);
                ejecutoCliente.start();
            }
        } catch (IOException ex) {
          //  Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RecibirArchivo(int puerto) {
        int puerto1=puerto;
        ServerSocket server;
        Socket connection;

       DataOutputStream output;
        BufferedInputStream bis;
        BufferedOutputStream bos;

       byte[] receivedData;
        int in;
        String file;

       try{
        //Servidor Socket en el puerto 5000
        server = new ServerSocket( puerto1 );
        while ( true ) {
        //Aceptar conexiones
        connection = server.accept();
        //Buffer de 1024 bytes
        receivedData = new byte[1024];
        bis = new BufferedInputStream(connection.getInputStream());
        DataInputStream dis=new DataInputStream(connection.getInputStream());
        //Recibimos el nombre del fichero
        file = dis.readUTF();
        file = file.substring(file.indexOf('\\')+1,file.length());
        //Para guardar fichero recibido
        bos = new BufferedOutputStream(new FileOutputStream(file));
        while ((in = bis.read(receivedData)) != -1){
        bos.write(receivedData,0,in);
        }
        bos.close();
        dis.close();
        }
        }catch (Exception e ) {
        System.err.println(e);
        }
        }
    
}
