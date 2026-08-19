package com.mycompany.galeria.de.arte;

public class Sala {
    
    private String numero;
    private String nombre;
    private int capacidadMaxObras;
    private Exhibicion exhibicionActual; // Ojo, esto se actualiza siempre. Cuando esta ocupada y vacia (null)
    
    public Sala(String numero, String nombre, int capacidadMaxObras) {
        this.numero = numero;
        this.nombre = nombre;
        this.capacidadMaxObras = capacidadMaxObras;
        this.exhibicionActual = null; //Inicia vacia / disponible.
    } 
}
