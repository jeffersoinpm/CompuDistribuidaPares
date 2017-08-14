package peertopeercd.archivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Cliente extends Thread{
    private String DatosCliente[];
    private Socket cliente;
    private PrintWriter enviar;
    private BufferedReader recibir;
    String mensaje="";
    public Cliente(String DatCli[]){
        DatosCliente=DatCli;
    }
    
    public void Principal(){
        System.out.println("se conectaaaa");
        try {
            cliente=new Socket(DatosCliente[0],Integer.parseInt(DatosCliente[1]));
            System.out.println("Conectado >> "+DatosCliente[0]);
            recibir=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            enviar=new PrintWriter(cliente.getOutputStream(), true);
            if(!(mensaje=JOptionPane.showInputDialog("Nodo4>> "+DatosCliente[0]+" "+DatosCliente[1])).equals(""))
                enviar.println(mensaje);  
                envioArchivo(DatosCliente[0], DatosCliente[1]);
        }catch (IOException ex){
            System.out.println("No pudo conectarse "+DatosCliente[0]);
        }
    }
    
    public void run(){
        Principal();
    }
    
    public void envioArchivo(String ip, String puerto){

        DataInputStream input;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        String ip2=ip;
        int puerto2=Integer.parseInt(puerto);
        int in;
        byte[] byteArray;
        //Fichero a transferir
        final String filename = "prueba.txt";

       try{
        final File localFile = new File( filename );
        Socket client = new Socket(ip2,puerto2);
        bis = new BufferedInputStream(new FileInputStream(localFile));
        bos = new BufferedOutputStream(client.getOutputStream());
        //Enviamos el nombre del fichero
        DataOutputStream dos=new DataOutputStream(client.getOutputStream());
        dos.writeUTF(localFile.getName());
        //Enviamos el fichero
        byteArray = new byte[8192];
        while ((in = bis.read(byteArray)) != -1){
        bos.write(byteArray,0,in);
        }

       bis.close();
        bos.close();

       }catch ( Exception e ) {
        System.err.println(e);
        }
        }

}
