package com.example.parkweiser;

public class OcupacionFecha {
    private String fecha;
    private float ocupacion;

    public OcupacionFecha(String fecha, float ocupacion) {
        this.fecha = fecha;
        this.ocupacion = ocupacion;
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