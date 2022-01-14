package com.example.parkweiser;


public class ReservaEn {
    private String matricula;
    private String entrada;
    private String salida;

    public ReservaEn(String matricula, String entrada, String salida) {
        this.matricula = matricula;
        this.entrada = entrada;
        this.salida = salida;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
}