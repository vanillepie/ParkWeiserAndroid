package com.example.parkweiser;

public class OcupacionFecha {
    private int plaza;
    private String fecha;
    private float ocupacion;

    public OcupacionFecha(int plaza, String fecha, float ocupacion) {
        this.plaza = plaza;
        this.fecha = fecha;
        this.ocupacion = ocupacion;
    }

    public int getPlaza() {
        return plaza;
    }
    public void setPlaza(int plaza) {
        this.plaza = plaza;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public float getOcupacion() {
        return ocupacion;
    }
    public void setOcupacion(float ocupacion) {
        this.ocupacion = ocupacion;
    }
}