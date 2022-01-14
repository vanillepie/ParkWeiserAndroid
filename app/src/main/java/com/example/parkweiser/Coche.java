package com.example.parkweiser;

public class Coche {

    private String matricula;
    private Boolean esElectrico;
    private Boolean esMinusvalidos;


    public Coche(String matricula, Boolean esElectrico, Boolean esMinusvalidos) {
        this.matricula = matricula;
        this.esElectrico = esElectrico;
        this.esMinusvalidos = esMinusvalidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public Boolean getEsElectrico() {
        return esElectrico;
    }

    public Boolean getEsMinusvalidos() {
        return esMinusvalidos;
    }
}
