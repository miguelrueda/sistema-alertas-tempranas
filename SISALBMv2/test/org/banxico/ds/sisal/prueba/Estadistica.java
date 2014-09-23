package org.banxico.ds.sisal.prueba;

public class Estadistica {

    private String fabricante;
    private int cuenta;

    public Estadistica() {
    }

    public Estadistica(String fabricante, int cuenta) {
        this.fabricante = fabricante;
        this.cuenta = cuenta;
    }

    public int getCuenta() {
        return cuenta;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public String toString() {
        return "Estadistica{" + "fabricante=" + fabricante + ", cuenta=" + cuenta + '}';
    }
}
