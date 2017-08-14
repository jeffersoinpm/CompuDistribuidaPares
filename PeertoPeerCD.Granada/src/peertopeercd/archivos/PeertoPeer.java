
package peertopeercd.archivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import peertopeercd.conexion.Conexion;
public class PeertoPeer {
    public static void main(String[] args) throws SQLException, UnknownHostException {
        try {
            String ClienteUnirse[];
            Conexion conn= new Conexion();
            int numeroServidor,numeroNodo;
            conn.ConexionBase();
            numeroServidor=conn.insertar();
            numeroNodo=conn.NumeroNodo();
            Servidor LevantarServicio=new Servidor(numeroServidor);
            LevantarServicio.start();
            ClienteUnirse=conn.puertoprimero();
            System.out.println("a quien conectarse>> "+ClienteUnirse[0]+" "+ClienteUnirse[1]);
            Cliente nuevoCliente1=new Cliente(ClienteUnirse);
            nuevoCliente1.start();
            do{
                sleep(30000);
                ClienteUnirse=conn.puertoultimo();
            }while(ClienteUnirse[0].equals(""));
            System.out.println("a quien conectarse >> "+ClienteUnirse[0]+" "+ClienteUnirse[1]);
            Cliente nuevoCliente2=new Cliente(ClienteUnirse);
            nuevoCliente2.start();
        } catch (InterruptedException ex) {
            //Logger.getLogger(PeertoPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}