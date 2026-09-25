package com.mycompany.galeria.de.arte;
import java.util.ArrayList;

public class Exhibicion {
    private String tematica;
    private int capacidadMaxima;
    private ArrayList<Obra> obrasExhibidas;

    public Exhibicion(String tematica, int capacidadMaxima) {
        this.tematica = tematica;
        this.capacidadMaxima = capacidadMaxima;
        this.obrasExhibidas = new ArrayList<>();
    }
    
    //Getters

    public String getTematica() {
        return this.tematica;
    }

    public int getCapacidadMaxima() {
        return this.capacidadMaxima;
    }
    
    public ArrayList<Obra> getObrasExhibidas() {
        return new ArrayList<>(this.obrasExhibidas);
    }

    //Setters

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }
    
    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
}
