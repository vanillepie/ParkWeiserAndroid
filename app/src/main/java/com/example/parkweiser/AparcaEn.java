package com.example.parkweiser;
import java.sql.Timestamp;

public class AparcaEn {
    private String matricula;
    private int parking;
    private Timestamp entrada;
    private Timestamp salida;
    private float precio;

    public AparcaEn(String matricula, int parking) {
        this.matricula = matricula;
        this.parking = parking;
        this.entrada = new Timestamp(System.currentTimeMillis());
        this.salida = null;
        this.precio = -1;
    }

    public AparcaEn(String matricula, int parking, Timestamp entrada, Timestamp salida, float precio) {
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

    public Timestamp getEntrada() {
        return entrada;
    }

    public void setEntrada(Timestamp entrada) {
        this.entrada = entrada;
    }

    public Timestamp getSalida() {
        return salida;
    }

    public void setSalida(Timestamp salida) {
        this.salida = salida;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
