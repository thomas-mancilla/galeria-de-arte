package com.mycompany.galeria.de.arte;

public class Obra {

    private Artista autor;
    private String titulo;
    private EstadoObra estado;
    private int precio;
    private int anioCreacion;
    private String id;
    
    public Obra(Artista autor, String titulo, EstadoObra estado, int precio, int anioCreacion) {
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.precio = precio;
        this.anioCreacion = anioCreacion;
        this.id = java.util.UUID.randomUUID().toString();
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
    public String getId() {
        return id;
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
    public void setId(String id) {
        this.id = id;
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
