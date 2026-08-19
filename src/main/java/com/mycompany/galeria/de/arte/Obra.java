package com.mycompany.galeria.de.arte;

public class Obra {

    private Artista autor;
    private String titulo;
    private String estado;
    private int precio;
    private int anioCreacion;
    
    public Obra(Artista autor, String titulo, String estado, int Precio, int anioCreacion) {
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.precio = precio;
        this.anioCreacion = anioCreacion;
        
    }
}
