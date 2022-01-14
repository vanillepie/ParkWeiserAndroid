package com.example.parkweiser;

import java.util.ArrayList;

public class Plaza {
    private int id;
    private int parking;
    private ArrayList<OcupacionFecha> valores = new ArrayList<>();


    public Plaza(int id, int parking) {
        this.id = id;
        this.parking = parking;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getParking() {
        return parking;
    }
    public void setParking(int parking) {
        this.parking = parking;
    }
    public ArrayList<OcupacionFecha> getValores() {
        return valores;
    }
    public void setValores(ArrayList<OcupacionFecha> valores) {
        this.valores = valores;
    }
}
