package com.mycompany.galeria.de.arte;

public class Artista {

    private String nombre;
    private String nacionalidad;
    private int anioNacimiento;

    public Artista(String nombre, String nacionalidad, int anioNacimiento) {
        setNombre(nombre);
        setNacionalidad(nacionalidad);
        setAnioNacimiento(anioNacimiento);
    }

    // Setters
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artista no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La nacionalidad no puede estar vacía.");
        }
        this.nacionalidad = nacionalidad.trim();
    }

    public void setAnioNacimiento(int anioNacimiento) {
        if (anioNacimiento < 0 || anioNacimiento > 2026) {
            throw new IllegalArgumentException("El año de nacimiento no es válido.");
        }
        this.anioNacimiento = anioNacimiento;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public int getAnioNacimiento() {
        return anioNacimiento;
    }
}