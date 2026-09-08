package com.mycompany.galeria.de.arte;

public class Exhibicion {
    private String tematica;
    private ArrayList<Obra> obrasExhibidas;

    public Exhibicion(String nombre, String tematica) {
        this.nombre = nombre;
        this.tematica = tematica;
        this.obrasExhibidas = new ArrayList<>();
    }
    
    //Getters

    public String getTematica() {
        return tematica;
    }

    public ArrayList<Obra> getObrasExhibidas() {
        return obrasExhibidas;
    }

    //Setters

    public void setTematica(String tematica) {
        this.tematica = tematica;
    } 
}
