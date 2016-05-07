package com.example.juanse.sairco;

/**
 * Created by juanse on 29/04/2016.
 */
public class Salon {

    public String ID;
    public String nombre;
    public String ubicacion;

    public Salon() {
    }

    public Salon(String ID, String nombre, String ubicacion) {
        this.ID = ID;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
