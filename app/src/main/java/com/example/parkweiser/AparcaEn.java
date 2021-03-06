package com.example.parkweiser;
import java.sql.Timestamp;

public class AparcaEn {
    private String matricula;
    private int parking;
    private String entrada;
    private String salida;
    private float precio;

    public AparcaEn(String matricula, int parking, String entrada, String salida, float precio) {
        this.matricula = matricula;
        this.parking = parking;
        this.entrada = entrada;
        this.salida = salida;
        this.precio = precio;
    }


    public String getMatricula() {
        return String.valueOf(matricula);
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
