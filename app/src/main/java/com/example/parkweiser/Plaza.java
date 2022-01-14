package com.example.parkweiser;

import java.util.ArrayList;

public class Plaza {
    private int id;
    private ArrayList<OcupacionFecha> valores;


    public Plaza(int id, ArrayList<OcupacionFecha> valores) {
        this.id = id;
        this.valores = valores;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<OcupacionFecha> getValores() {
        return valores;
    }
    public void setValores(ArrayList<OcupacionFecha> valores) {
        this.valores = valores;
    }
}
