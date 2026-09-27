package com.mycompany.galeria.de.arte;

public class Escultura extends Obra {
    public Escultura(Artista autor, String titulo, EstadoObra estado, int precio, int anioCreacion) {
        super(autor, titulo, estado, precio, anioCreacion);
    }

    @Override
    public double calcularCostoSeguro() {
        return (getPrecio() * 0.05) + 400.0; // 5% + base alta por traslado
    }
}