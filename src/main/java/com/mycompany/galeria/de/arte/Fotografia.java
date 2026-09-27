package com.mycompany.galeria.de.arte;

public class Fotografia extends Obra {
    public Fotografia(Artista autor, String titulo, EstadoObra estado, int precio, int anioCreacion) {
        super(autor, titulo, estado, precio, anioCreacion);
    }

    @Override
    public double calcularCostoSeguro() {
        return (getPrecio() * 0.01) + 50.0; // 1% + base económica
    }
}