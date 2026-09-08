package com.mycompany.galeria.de.arte;
import java.util.ArrayList;

public class Sala {
    
    private String numero;
    private String nombre;
    private int capacidadMaxObras;
    private ArrayList<Exhibicion> exhibiciones; 
    
    public Sala(String numero, String nombre, int capacidadMaxObras) {
        this.numero = numero;
        this.nombre = nombre;
        this.capacidadMaxObras = capacidadMaxObras;
        this.exhibiciones = new ArrayList<>(); //Inicia vacia / disponible.
    
    //Getters 
    
    }
    public String getNumero() {
        return numero;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getCapacidadMaxObras() {
        return capacidadMaxObras;
    }

    public ArrayList<Exhibicion> getExhibiciones() {
        return exhibiciones;
    }
    
    //Setters (Para el ArrayList no existe. Serian sus metodos de agregar o eliminar)
    
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxObras(int capacidadMaxObras) {
        this.capacidadMaxObras = capacidadMaxObras;
    }
}
