package proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wilmer
 */
public class Conexion {
    Connection conn = null;
    public void realizaConexion() {
        String urlDatabase = "jdbc:postgresql://52.41.80.65:5432/iOSProyect";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDatabase, "postgres", "root");
        } catch (Exception e) {
            System.out.println("Ocurrio un error en la conexion: " + e.getMessage());
        }
    }
    public ArrayList obtenerDatos() {

        try {
            realizaConexion();
            
            ArrayList<Base> datos = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM servidor");
            String nombre;
            int numeroservidor;
            String ipservidor;
            int puertoservidor;
            String numerospalabras;
            while (rs.next()) {
                nombre = rs.getString("nombre");
                numeroservidor = rs.getInt("numeroservidor");
                ipservidor = rs.getString("ipservidor");
                puertoservidor = rs.getInt("puertoservidor");
                numerospalabras = rs.getString("numerospalabras");
                datos.add(new Base(nombre, numeroservidor, ipservidor, puertoservidor, numerospalabras));
            }
            conn.close();
            return datos;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void insertar(String nombre,
            int numeroservidor,
            String ipservidor,
            int puertoservidor,
            String numerospalabras) {
        
        try {
            realizaConexion();
            try (Statement stmt = conn.createStatement()) {
                String sqlQuery = "insert into servidor(nombre,numeroservidor,ipservidor,puertoservidor,numerospalabras) values('"+nombre+"' , '"+numeroservidor+"', '"+ipservidor+"', '"+puertoservidor+"', '"+numerospalabras+"')";
                stmt.executeUpdate(sqlQuery);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}