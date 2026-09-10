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
    }
    
    //Getters 
    
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
    
    //Setters (Para el ArrayList no existe. Serian sus metodos de agregar)
    
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxObras(int capacidadMaxObras) {
        this.capacidadMaxObras = capacidadMaxObras;
    }
    
    public void agregarExhibicion(Exhibicion exhibicion) {
        this.exhibiciones.add(exhibicion);
    }

    //METODOS :
    
    public void listarExhibiciones() {
        if (exhibiciones.isEmpty()) {
            System.out.println("No hay exhibiciones en esta sala.");
            return;
        }
        for (Exhibicion exhibicion : exhibiciones) {
            System.out.println("Temática: " + exhibicion.getTematica() + " | Capacidad: " + exhibicion.getCapacidadMaxima());
        }
    }

    public Exhibicion buscarExhibicion(String tematica) {
        for (Exhibicion exhibicion : exhibiciones) {
            if (exhibicion.getTematica().equalsIgnoreCase(tematica)) {
                return exhibicion;
            }
        }
        return null; // Retorna nulo si no existe
    }

    public boolean eliminarExhibicion(String tematica) {
        Exhibicion exhibicion = buscarExhibicion(tematica);
        if (exhibicion != null) {
            exhibiciones.remove(exhibicion);
            return true;
        }
        return false;
    }

    public boolean editarExhibicion(String tematicaOriginal, String nuevaTematica, int nuevaCapacidad) {
        Exhibicion exhibicion = buscarExhibicion(tematicaOriginal);
        if (exhibicion != null) {
            exhibicion.setTematica(nuevaTematica);
            exhibicion.setCapacidadMaxima(nuevaCapacidad);
            return true;
        }
        return false;
    }
}