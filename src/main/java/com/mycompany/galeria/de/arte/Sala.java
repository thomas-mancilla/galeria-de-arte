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
        this.exhibiciones = new ArrayList<>();
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
        return new ArrayList<>(this.exhibiciones);
    }
    
    //Setters

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidadMaxObras(int capacidadMaxObras) {
        this.capacidadMaxObras = capacidadMaxObras;
    }
    
    // La temática identifica la exhibición dentro de esta sala.
    public boolean agregarExhibicion(Exhibicion exhibicion) {
        if (exhibicion == null || exhibicion.getTematica() == null || exhibicion.getTematica().trim().isEmpty() || buscarExhibicion(exhibicion.getTematica()) != null || exhibicion.getCapacidadMaxima() <= 0 || exhibicion.getCapacidadMaxima() > capacidadMaxObras || exhibicion.getObrasExhibidas().size() > exhibicion.getCapacidadMaxima() || cantidadObrasExhibidas() + exhibicion.getObrasExhibidas().size() > capacidadMaxObras) {
            return false;
        }
        exhibicion.setTematica(exhibicion.getTematica().trim());
        return exhibiciones.add(exhibicion);
    }

    public ArrayList<Exhibicion> listarExhibiciones() {
        return new ArrayList<>(exhibiciones);
    }

    public Exhibicion buscarExhibicion(String tematica) {
        if (tematica == null) return null;
        for (Exhibicion exhibicion : exhibiciones) {
            if (exhibicion.getTematica().equalsIgnoreCase(tematica.trim())) {
                return exhibicion;
            }
        }
        return null;
    }

    public boolean eliminarExhibicion(String tematica) {
        Exhibicion exhibicion = buscarExhibicion(tematica);
        if (exhibicion == null || !exhibicion.getObrasExhibidas().isEmpty()) {
            return false;
        }
        return exhibiciones.remove(exhibicion);
    }

    public boolean editarExhibicion(String tematicaOriginal, String nuevaTematica, int nuevaCapacidad) {
        Exhibicion exhibicion = buscarExhibicion(tematicaOriginal);
        if (exhibicion == null || nuevaTematica == null || nuevaTematica.trim().isEmpty() || nuevaCapacidad <= 0 || nuevaCapacidad > capacidadMaxObras || nuevaCapacidad < exhibicion.getObrasExhibidas().size()) {
                return false;
        }
        Exhibicion repetida = buscarExhibicion(nuevaTematica);
        if (repetida != null && repetida != exhibicion) {
            return false;
        }
        exhibicion.setTematica(nuevaTematica.trim());
        exhibicion.setCapacidadMaxima(nuevaCapacidad);
        return true;
    }

    public int cantidadObrasExhibidas() {
        int cantidad = 0;
        for (Exhibicion exhibicion : exhibiciones) {
            cantidad += exhibicion.getObrasExhibidas().size();
        }
        return cantidad;
    }
}

