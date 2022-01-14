package com.example.parkweiser;

public class Conductor {
    private String DNI;
    private String clave;
    private String nombre;
    private int telefono;
    private String tarjeta;

    // constructors
    public Conductor(String DNI, String clave, String nombre, int telefono, String tarjeta)
    {
        this.DNI = DNI;
        this.clave = clave;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Para devolver objeto vacío si la contraseña es incorrecta
    public Conductor(String DNI) {
        this.DNI = DNI;
        this.clave = "";
        this.nombre = "";
        this.telefono = 0;
    }

    public String getDNI()
    {
        return DNI;
    }

    public void setDNI(String DNI)
    {
        this.DNI = DNI;
    }

    public String getClave()
    {
        return clave;
    }

    public void setClave(String clave)
    {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
}
