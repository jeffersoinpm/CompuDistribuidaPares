package proyecto.compu;
/**
 *
 * @author Bryan
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class RegistrarDriver {
    
  /** Conexion a base de datos */
  private static Connection conn = null;
  /** Para obtener los resultados de las consultas SQL de la base de datos */
  private ResultSet resultSet = null;
  /** Para enviar comandos SQL a la base de datos */
  //private Statement statement = null;
  
 public void connectDatabase() {
        Conexion();
    } 

    private void Conexion() {
        try {
            // Registramos el driver de PostgresSQL
            try { 
                Class.forName("org.postgresql.Driver"); /** Driver para la conexion*/
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            /** Cadena de conexión *//** Usuario postgreSQL *//** Nombre de la base de datos *//** Contraseña postgreSQL */
            conn = DriverManager.getConnection("jdbc:postgresql://52.41.80.65:5432/iOSProyect","postgres", "root");
 
            boolean valid = conn.isValid(50000);
            System.out.println(valid ? "Conectado" : "No se pudo conectar");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    } 
    
    private static boolean Actualizar(Statement st, String queryConsulta) {
        boolean res = false;
        try {
            st.executeUpdate(queryConsulta);
            res = true;
        } catch (SQLException e) {
            System.out.println("Error con: " + queryConsulta);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
    /**
     * Probando la conexión en Java a PostgreSQL especificando el host y el puerto.
     */
   
    public boolean Insertar(String NumMensaje, String Ip, Integer Puerto, Integer NumeroDePalabrasInicial, Integer NumerDePalabrasFinal) throws SQLException {
        String query = 
        "insert into servidor('nombre','numeroservidor','ipservidor','puertoservidor','numerospalabras')"+
        " values('Bryan','" + NumMensaje + "','" + Ip + "','" + Puerto + "','" + NumeroDePalabrasInicial + "-" + NumerDePalabrasFinal + "')";
        connectDatabase();
        Statement st = conexion();
        boolean mensaje = false;
        try {
            Actualizar(st, query);
            mensaje = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        st.close();
        return mensaje;
    }
    
    private static ResultSet ConsultarCadena(Statement st, String queryConsulta) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(queryConsulta);
        } catch (SQLException e) {
            System.out.println("Error: " + queryConsulta + "SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }
    
    /*public  void conectar() {
        RegistrarDriver postgres = new RegistrarDriver();
        postgres.connectDatabase(); 
        //javaPostgreSQLBasic.connectDatabase("52.41.80.65", "5432", "customerdb","postgres", "root");
        //RegistrarDriver pgf = new RegistrarDriver();        
        //System.out.println( pgf.CallFunction());
    }*/
 
    public static Statement conexion() {
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexión incorrecta.");
            e.printStackTrace();
        }
        return st;
    }
    
      public ResultSet Servidor (){
        String query = 
        "select * from servidor";

        connectDatabase();
        Statement st = conexion();
        ResultSet rs = ConsultarCadena(st, query);
        return rs;
    }
    public ResultSet consultarUsuario(String usuario) {
        String query = 
        "select * from servidor where numeroservidor=(select max(numeroservidor) from servidor)";

        connectDatabase();
        Statement st = conexion();
        ResultSet rs = ConsultarCadena(st, query);
        return rs;
    }
     
    private static void ConsultaCerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.print("Error" + e.getMessage());
            }
        }
    }

    private static void PostgresCerrar(java.sql.Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.print("Error" + e.getMessage());
            }
        }
    }
    
    /**
     * Método para establecer la conexión a la base de datos mediante el paso de parámetros.
     * 
     * @param host <code>String</code> Nombre del host o ip.
     * @param port <code>String</code> Puerto en el que escucha la base de datos.
     * @param database <code>String</code> Nombre de la base de datos para la conexión.
     * @param user <code>String</code> Nombre de usuario.
     * @param password  <code>String</code> Password del usuario.
     */
   /*public void connectDatabase(String host, String port, String database,
            String user, String password) {
        String url = "";
        try {
            // Registramos el driver de PostgresSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            Connection connection = null;
            url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(
                    url,
                    user, password);           
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) { 
            System.out.println("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
        }
    }*/
}
