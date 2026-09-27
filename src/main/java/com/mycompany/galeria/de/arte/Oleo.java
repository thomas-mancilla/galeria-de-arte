package com.mycompany.galeria.de.arte;

public class Oleo extends Obra {
    public Oleo(Artista autor, String titulo, EstadoObra estado, int precio, int anioCreacion) {
        super(autor, titulo, estado, precio, anioCreacion);
    }

    @Override
    public double calcularCostoSeguro() {
        return (getPrecio() * 0.03) + 150.0; // 3% + base
    }
}