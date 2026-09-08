package com.mycompany.galeria.de.arte;

public class Obra {

    private String autor;
    private String titulo;
    private String estado;
    private int precio;
    private int anioCreacion;
    
    public Obra(String autor, String titulo, String estado, int Precio, int anioCreacion) {
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.precio = precio;
        this.anioCreacion = anioCreacion;
        
    }
    
    //Getters
    
    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstado() {
        return estado;
    }

    public int getPrecio() {
        return precio;
    }

    public int getAnioCreacion() {
        return anioCreacion;
    }

    // Setters
    
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setAnioCreacion(int anioCreacion) {
        this.anioCreacion = anioCreacion;
    }
    
}
