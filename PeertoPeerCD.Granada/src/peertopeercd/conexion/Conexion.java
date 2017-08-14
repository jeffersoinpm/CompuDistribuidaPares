/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeercd.conexion;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.text.html.HTML.Tag.SELECT;

public class Conexion  {
    private Connection conn = null; 
    private Statement st = null;
    private int numeroServidor = 0;
    public Statement ConexionBase(){
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://52.41.80.65:5432/iOSProyect", "postgres", "root");
                st = conn.createStatement();
                System.out.println("conexión exitosa");
            } catch (Exception e) {
                System.out.println("Ocurrio un error : "+e.getMessage());
            }
            return st;
}
    public int insertar() throws SQLException, UnknownHostException {
            numeroServidor = maximoServidor()+1;
            int numeroPuerto = 0;
            numeroPuerto = puertoServidor()+1;
            if(numeroPuerto==1)
                numeroPuerto=4447;
            InetAddress address = InetAddress.getLocalHost(); 
            String hostIP = address.getHostAddress();
            System.out.println( "IP: " + hostIP +" "+numeroServidor);
            String insert="insert into servidor values('Nodo4',"+numeroServidor+",'"+hostIP+"',"+numeroPuerto+",'11-20')";
        try {
            st.executeQuery(insert);                                
        } catch (Exception e){
            //e.printStackTrace();
        }
        return numeroPuerto;    
    }
    
    public int NumeroNodo(){
        int numNodo=0;
        try {
            InetAddress address = InetAddress.getLocalHost();
            String hostIP = address.getHostAddress();
            ResultSet rs = null;
            String consulta="select numeroservidor from servidor where ipservidor='"+hostIP+"'";
            try {
                rs = st.executeQuery(consulta);
                while(rs.next())
                {
                    String str = rs.getString("numeroservidor");
                    numNodo = Integer.parseInt(str);
                }
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        } catch (UnknownHostException ex) {
        
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numNodo;
    }

public int maximoServidor() throws SQLException {
        ResultSet rs = null;
        String consulta="select numeroservidor from servidor where numeroservidor=(select max(numeroservidor) from servidor)";
        //Statement st = ConexionBase();
        int numeroServidor=0;
        try {
             rs = st.executeQuery(consulta);
             while(rs.next())
                {
                    String str = rs.getString("numeroservidor");
                    numeroServidor = Integer.parseInt(str);
                }                           
        } catch (Exception e) {
        
            e.printStackTrace();
        }
        //st.close();        
        return numeroServidor;
    }
    
public int puertoServidor() throws SQLException {
        ResultSet rs = null;
        String consulta="select puertoservidor from servidor where puertoservidor=(select max(puertoservidor) from servidor)";
        //Statement st = ConexionBase();
        int numeroPuerto=0;
        try {
             rs = st.executeQuery(consulta);
             while(rs.next())
                {
                    String str = rs.getString("puertoservidor");
                    numeroPuerto = Integer.parseInt(str);
                }   
                      
        } catch (Exception e) {
           
            e.printStackTrace();
        }
        //st.close();       
        return numeroPuerto;
    }

public ResultSet consultarServidores() throws SQLException {
        String consulta = "select * from servidor";
        Statement st = ConexionBase();
        ResultSet rs = st.executeQuery(consulta);
        return rs;
    }

public String[] puertoprimero() throws SQLException {
        ResultSet rs = null;
        String consulta="select ipservidor,puertoservidor from servidor where numeroservidor='"+(numeroServidor-1)+"'";
        String ips2[]={"",""};
        try {
             rs = st.executeQuery(consulta);
            while(rs.next()){
                    ips2[1]=rs.getString("puertoservidor");
                    ips2[0]=rs.getString("ipservidor");
            }
            System.out.println("servidor anterior:  "+ips2[1]+" "+ips2[0]);              
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ips2;
    }
    public String[] puertoultimo() throws SQLException {
        ResultSet rs = null;
        String consulta="select ipservidor,puertoservidor from servidor where numeroservidor='"+(numeroServidor+1)+"'";
        String IpUnirse[]={"",""};
        try {
             rs = st.executeQuery(consulta);
            while(rs.next()){
                    IpUnirse[1] = rs.getString("puertoservidor");
                    IpUnirse[0]=rs.getString("ipservidor");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IpUnirse;
    }
}

