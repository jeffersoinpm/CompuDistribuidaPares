/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad;

/**
 *
 * @author Wilmer
 */
public class Servidor {

    String nombre;
    int numeroservidor;
    String ipservidor;
    int puertoservidor;
    String numerospalabras;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroservidor() {
        return numeroservidor;
    }

    public void setNumeroservidor(int numeroservidor) {
        this.numeroservidor = numeroservidor;
    }

    public String getIpservidor() {
        return ipservidor;
    }

    public void setIpservidor(String ipservidor) {
        this.ipservidor = ipservidor;
    }

    public int getPuertoservidor() {
        return puertoservidor;
    }

    public void setPuertoservidor(int puertoservidor) {
        this.puertoservidor = puertoservidor;
    }

    public String getNumerospalabras() {
        return numerospalabras;
    }

    public void setNumerospalabras(String numerospalabras) {
        this.numerospalabras = numerospalabras;
    }

    public Servidor(String nombre, int numeroservidor, String ipservidor, int puertoservidor, String numerospalabras) {
        this.nombre = nombre;
        this.numeroservidor = numeroservidor;
        this.ipservidor = ipservidor;
        this.puertoservidor = puertoservidor;
        this.numerospalabras = numerospalabras;
    }
    

}
