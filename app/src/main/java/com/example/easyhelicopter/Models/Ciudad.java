package com.example.easyhelicopter.Models;

import java.util.ArrayList;
import java.util.List;

public class Ciudad {
    private int id;
    private String nombre;
    private List<Puerto> puertos = new ArrayList<>();


    public Ciudad() {

    }

    public Ciudad(String nombre) {
        this.setNombre(nombre);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Puerto> getPuertos() {
        return puertos;
    }

    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
    }
}
