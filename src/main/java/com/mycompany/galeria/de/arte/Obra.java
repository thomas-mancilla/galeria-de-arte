package com.mycompany.galeria.de.arte;

public class Obra {

    private Artista autor;
    private String titulo;
    private EstadoObra estado;
    private int precio;
    private int anioCreacion;
    
    public Obra(Artista autor, String titulo, EstadoObra estado, int precio, int anioCreacion) {
        this.autor = autor;
        this.titulo = titulo;
        this.estado = EstadoObra.GUARDADA;
        this.precio = precio;
        this.anioCreacion = anioCreacion;
    }

    public Artista getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public EstadoObra getEstado() {
        return estado;
    }

    public int getPrecio() {
        return precio;
    }

    public int getAnioCreacion() {
        return anioCreacion;
    }

    public void setAutor(Artista autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setAnioCreacion(int anioCreacion) {
        this.anioCreacion = anioCreacion;
    }

    @Override
    public String toString() {
        return "Autor: " + autor
                + " | Título: " + titulo
                + " | Estado: " + estado
                + " | Precio: " + precio
                + " | Año: " + anioCreacion;
    }
}
