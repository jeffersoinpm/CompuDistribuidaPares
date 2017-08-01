package proyecto;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServidorPeer {

    public static Integer contadorClientes = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        Conexion obj = new Conexion();
        //obj.realizaConexion();
        ArrayList<Base> datosBase = obj.obtenerDatos();
        for (Base d : datosBase)
            System.out.println(d.toString());
        int portNumberServer = 4447;
        Integer numeroDeServidor = 1;
        String hostName1 = "";
        Integer portNumberServer1 = 0;
        String hostName2 = "";
        Integer portNumberServer2 = 0;
        boolean banderaHostListo = false;
        if (datosBase.isEmpty()) {
            System.out.println("soy el primer servidor");
            obj.insertar("Test", numeroDeServidor, InetAddress.getLocalHost().getHostAddress(), portNumberServer, "51-60");
            System.out.println("puerto en el que escucho " + portNumberServer);
            Thread server1 = new Thread(new Servidor(portNumberServer));
            server1.start();
        } else if(datosBase.size() == 1){
            System.out.println("soy el segundo servidor");
            numeroDeServidor = datosBase.get(datosBase.size() - 1).getNumeroservidor() + 1;
            portNumberServer = datosBase.get(datosBase.size() - 1).getPuertoservidor() + 1;
            System.out.println("puerto en el que escucho " + portNumberServer);
            obj.insertar("Test", numeroDeServidor, InetAddress.getLocalHost().getHostAddress(), portNumberServer, "51-60");
            Thread server = new Thread(new Servidor(portNumberServer));
            server.start();
            
            hostName1 = datosBase.get(datosBase.size() - 1).getIpservidor();
            portNumberServer1 = datosBase.get(datosBase.size() - 1).getPuertoservidor();
            System.out.println("host anterior al que me conecto " + portNumberServer1);
            Thread client1 = new Thread(new Cliente(hostName1, portNumberServer1));
            client1.start();
        } else {
            System.out.println("soy el ultimo servidor");
            numeroDeServidor = datosBase.get(datosBase.size() - 1).getNumeroservidor() + 1;
            portNumberServer = datosBase.get(datosBase.size() - 1).getPuertoservidor() + 1;
            System.out.println("puerto en el que escucho " + portNumberServer);
            obj.insertar("Test", numeroDeServidor, InetAddress.getLocalHost().getHostAddress(), portNumberServer, "51-60");
            Thread server = new Thread(new Servidor(portNumberServer));
            server.start();
            
            hostName1 = datosBase.get(datosBase.size() - 1).getIpservidor();
            portNumberServer1 = datosBase.get(datosBase.size() - 1).getPuertoservidor();
            System.out.println("host anterior al que me conecto " + portNumberServer1);
            Thread client1 = new Thread(new Cliente(hostName1, portNumberServer1));
            client1.start();
            
            hostName2 = datosBase.get(0).getIpservidor();
            portNumberServer2 = datosBase.get(0).getPuertoservidor();
            System.out.println("host posterior al que me conecto" + portNumberServer2);
            Thread client2 = new Thread(new Cliente(hostName2, portNumberServer2));
            client2.start();
        }
    }
}
