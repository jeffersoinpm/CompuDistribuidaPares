/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.compu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bryan
 */
public class ServidorSocket implements Runnable{
    
    private static Integer puerto;   
    public ServidorSocket(){}
    
    public ServidorSocket(Integer puerto)
    {
        this.puerto = puerto;
    }
    
    public void run() {
        
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(this.puerto);
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        Base(ss);

        
    }

    private void Base(ServerSocket ss) throws NumberFormatException {
        try {
            Integer puerto = 0;
            RegistrarDriver rd = new RegistrarDriver();
            ResultSet rs = rd.Servidor();
            while (true || rs.next()) {
                Socket cs = ss.accept();
                List<String> lineas = new ArrayList((Collection) (cs.getInputStream()));
                String linea = rs.getString(
                        "nombre")
                        + "\t" + rs.getInt("numeroservidor")
                        + "\t" + rs.getString("ipservidor")
                        + "\t" + rs.getInt("puertoservidor");
                if (linea.split("\t")[2].equals(cs.getInetAddress().getHostAddress())) {
                    puerto = Integer.parseInt(linea.split("\t")[3]);
                    //new MultiServerThread(ss.accept(), puerto).start();
                }
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
