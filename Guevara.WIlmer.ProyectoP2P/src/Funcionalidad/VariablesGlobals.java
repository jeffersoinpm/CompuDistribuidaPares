/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidad;

/**
 *
 * @author Wilmer
 */
import Conection.Data;
import Entidad.Servidor;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VariablesGlobals {

    public static int UDPPORT = 4446;
    public static int TCPPORT = 4447;
    // delay in milliseconds between broadcasts
    public static final int UDPINTERVAL = 1000;
    public static final InetAddress addresstoSend;
    private static ArrayList<Servidor> datos;

    public static void inicializar() throws UnknownHostException {
        Data r = new Data();
        VariablesGlobals.datos = r.obtenerDatos();

        Data data = new Data();
        Integer numeroDeServidor = 0;
        for (Servidor d : datos) {

            System.out.println("id:" + d.getNumeroservidor() + "nombre" + d.getIpservidor() + "puerto:" + d.getPuertoservidor());
        }
        if (datos.size() == 0) {
            data.insert("Wilmer", (numeroDeServidor + 1), InetAddress.getLocalHost().getHostAddress(), TCPPORT, "10-20");

        } else {
            numeroDeServidor = datos.size() + 1;

            UDPPORT = datos.get(datos.size() - 1).getPuertoservidor();
            TCPPORT += datos.size();
            data.insert("Wilmer", (numeroDeServidor), InetAddress.getLocalHost().getHostAddress(), (TCPPORT), "10-20");
        }
    }

    static {
        // create broadcast address object refrencing the local machine's
        // broadcasting address for use with UDP
        addresstoSend = getAddresstoSend();
        assert (addresstoSend != null);
    }

    private static InetAddress getAddresstoSend() {
        try {
            inicializar();

        } catch (UnknownHostException ex) {
            Logger.getLogger(VariablesGlobals.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<NetworkInterface> interfaces = new ArrayList<>();
        try {

            interfaces.addAll(Collections.list(
                    NetworkInterface.getNetworkInterfaces()));
            
           

//            for (NetworkInterface aInterface : interfaces) {
//                 System.out.println("Interface:"+aInterface.getInetAddresses());
//            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
        for (NetworkInterface nic : interfaces) {
            try {
                if (!nic.isUp() || nic.isLoopback()) {
                    continue;
                }
            } catch (SocketException ex) {
                continue;
            }
            for (InterfaceAddress ia : nic.getInterfaceAddresses()) {
                if (ia == null || ia.getBroadcast() == null) {
                    continue;
                }
                return ia.getAddress();
            }
        }
        return null;
    }
}
