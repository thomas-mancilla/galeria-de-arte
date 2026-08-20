package com.mycompany.galeria.de.arte;

public class Exhibicion {
    private String nombre;
    private String tematica;
    private ArrayList<Obra> obrasExhibidas;

    public Exhibicion(String nombre, String tematica) {
        this.nombre = nombre;
        this.tematica = tematica;
        this.obrasExhibidas = new ArrayList<>();
    }
}
