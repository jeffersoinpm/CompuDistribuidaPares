package proyecto.compu;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Chat {

    public static Integer contCli = 0;

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {
        chat();
    }

    private static void chat() throws NumberFormatException, SQLException, UnknownHostException {
        
        Integer numeroDeServidor = 0;
        int puerto = 4445,puertoServidor = 4446;
        Integer numeroLineas = 0;
        String linea = "";
        String hostName1 = "";
        Integer puertoServidor1 = 0;
        String hostName2 = "";
        Integer portNumberServer2 = 0;   
        List<String> lineas = new ArrayList<>();
        RegistrarDriver c = new RegistrarDriver();
        
        numeroLineas = ConsultarUsuario(c, hostName2, lineas, numeroLineas);
        
        ResultSet r = c.Servidor();
        
        if (r.next()) {
            
            System.out.println(" " + r.getString("nombre"));
            puerto += r.getInt("numeroservidor") + 1;
            puertoServidor += r.getInt("numeroservidor") + 1;
            linea = r.getString("nombre") + "\t" + r.getInt("numeroservidor") + 1 + "\t" + InetAddress.getLocalHost().getHostAddress() + "\t" + puertoServidor;
            boolean host = false;
            host = true;
            numeroDeServidor = r.getInt("numeroservidor") + 1;
        } else {
            puerto++;
            puertoServidor++;
            linea = "server" + "\t" + 1 + "\t" + InetAddress.getLocalHost().getHostAddress() + "\t" + puertoServidor;
            boolean host = false;
            host = true;
            numeroDeServidor = 1;
        }
        lineas.add(linea);
        numeroLineas++;

        
        if (numeroDeServidor == 0) {
            numeroDeServidor = 1;
        }
        hilosServidor(lineas, numeroDeServidor, hostName1, puertoServidor1, hostName2, numeroLineas, portNumberServer2, puertoServidor, c);
    }

    private static void hilosServidor(List<String> lineas, Integer numeroDeServidor, String hostName1, Integer puertoServidor1, String hostName2, Integer numeroLineas, Integer portNumberServer2, int puertoServidor, RegistrarDriver c) throws NumberFormatException, SQLException, UnknownHostException {
        LineasList(lineas, numeroDeServidor, hostName1, puertoServidor1, hostName2, numeroLineas, portNumberServer2);
        Thread threadS = new Thread(new ServidorSocket(puertoServidor));
        threadS.start();
        c.Insertar(numeroDeServidor.toString(), InetAddress.getLocalHost().getHostAddress(), puertoServidor, 50, 60);
    }

    private static Thread Hilos(String hostName1, Integer puertoServidor1, String hostName2, Integer portNumberServer2) {
        Thread ClienteHilo1 = new Thread(new ClienteSocket(hostName1, puertoServidor1));
        if (!hostName1.concat(puertoServidor1.toString()).equals(hostName2.concat(portNumberServer2.toString()))) {
            Thread ClienteHilo2 = new Thread(new ClienteSocket(hostName2, portNumberServer2));
            ClienteHilo2.start();
        }
        return ClienteHilo1;
    }
    
    private static void LineasList(List<String> lineas, Integer numeroDeServidor, String hostName1, Integer puertoServidor1, String hostName2, Integer numeroLineas, Integer portNumberServer2) throws NumberFormatException {
        if (lineas.size() > 1) {
            if (numeroDeServidor == 1) {
                hostName1 = lineas.get(numeroDeServidor).split("\t")[2];
                puertoServidor1 = lineas.get(numeroDeServidor).split("\t")[2] != null && !" ".equals(lineas.get(numeroDeServidor).split("\t")[2]) ? Integer.parseInt(lineas.get(numeroDeServidor).split("\t")[3]) : 0;
                hostName2 = lineas.get(numeroLineas - 1).split("\t")[2];
                portNumberServer2 = lineas.get(numeroLineas - 1).split("\t")[2] != null && !" ".equals(lineas.get(numeroLineas - 1).split("\t")[2]) ? Integer.parseInt(lineas.get(numeroLineas - 1).split("\t")[3]) : 0;
            } else if (numeroDeServidor > 1 && numeroDeServidor < numeroLineas) {
                hostName1 = lineas.get(numeroDeServidor).split("\t")[2];
                puertoServidor1 = lineas.get(numeroDeServidor).split("\t")[2] != null && !" ".equals(lineas.get(numeroDeServidor).split("\t")[2]) ? Integer.parseInt(lineas.get(numeroDeServidor).split("\t")[3]) : 0;
                hostName2 = lineas.get(numeroDeServidor - 2).split("\t")[2];
                portNumberServer2 = lineas.get(numeroDeServidor - 2).split("\t")[3] != null && !" ".equals(lineas.get(numeroDeServidor - 2).split("\t")[3]) ? Integer.parseInt(lineas.get(numeroDeServidor - 2).split("\t")[3]) : 0;
            } else if (numeroDeServidor != 0) {
                hostName1 = lineas.get(0).split("\t")[2];
                puertoServidor1 = lineas.get(0).split("\t")[2] != null && !" ".equals(lineas.get(0).split("\t")[2]) ? Integer.parseInt(lineas.get(0).split("\t")[3]) : 0;
                hostName2 = lineas.get(numeroDeServidor - 2).split("\t")[2];
                portNumberServer2 = lineas.get(numeroDeServidor - 2).split("\t")[2] != null && !" ".equals(lineas.get(numeroDeServidor - 2).split("\t")[2]) ? Integer.parseInt(lineas.get(numeroDeServidor - 2).split("\t")[3]) : 0;
            }

            Thread ClienteHilo1 = Hilos(hostName1, puertoServidor1, hostName2, portNumberServer2);
            ClienteHilo1.start();

        }
    }

    

    private static Integer ConsultarUsuario(RegistrarDriver c, String hostName2, List<String> lineas, Integer numeroLineas) throws SQLException {
        String linea;
        ResultSet rs = c.consultarUsuario(hostName2);
        while (rs.next()) {
            linea = rs.getString("nombre") + "\t" + rs.getInt("numeroservidor") + "\t" + rs.getString("ipservidor") + "\t" + rs.getInt("puertoservidor");
            lineas.add(linea);
            numeroLineas++;
        }
        return numeroLineas;
    }

}
