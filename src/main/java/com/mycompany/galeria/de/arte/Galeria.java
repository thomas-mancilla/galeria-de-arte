package com.mycompany.galeria.de.arte;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

class Galeria {
    private Map<String, Sala> mapaSalas;
    private Bodega bodega;
    private Map<String, Cliente> mapaClientes; 
    private ArrayList<Venta> registroVentas;
    private ArrayList<Prestamo> registroPrestamos;

    public Galeria() {
        mapaSalas = new HashMap<>(); //MAPA DE SALAS CONTIENE: SALAS
        this.bodega = new Bodega();
        this.mapaClientes = new HashMap<>();
        this.registroVentas = new ArrayList<>();
        this.registroPrestamos = new ArrayList<>();
    } 
    
    //Getters
    
    public Map<String, Sala> getMapaSalas() {
        return mapaSalas;
    }

    public Bodega getBodega() {
        return this.bodega;
    }

    public Map<String, Cliente> getMapaClientes() {
        return this.mapaClientes;
    }
    
    public ArrayList<Venta> getRegistroVentas() {
        return this.registroVentas;
    }
    
    public ArrayList<Prestamo> getRegistroPrestamos() {
        return this.registroPrestamos;
    }

    //METODOS DE SALA:
    // Agregar sala
    public boolean insertarSala(String id, Sala sala) {
        if (id == null || id.trim().isEmpty() || sala == null) {
            return false;
        }
        if (mapaSalas.containsKey(id)) {
            return false;
        }
        if (sala.getNombre() == null || sala.getNombre().trim().isEmpty() || sala.getCapacidadMaxObras() <= 0) {
            return false;
        }
        sala.setNumero(id);
        mapaSalas.put(id, sala);
        return true;
    }

    // Buscar sala por ID
    public Sala buscarSala(String id) {
        return mapaSalas.get(id);
    }

    // Listar todas las salas
    public ArrayList<Sala> listarSalas() {
        return new ArrayList<>(mapaSalas.values());
    }
    
    // Editar sala sin perder sus exhibiciones
    public boolean editarSala(String id, String nuevoNombre, int nuevaCapacidad) {
        Sala sala = mapaSalas.get(id);
        if (sala == null || nuevoNombre == null || nuevoNombre.trim().isEmpty() || nuevaCapacidad <= 0) {
            return false;
        }
        for (Exhibicion exhibicion : sala.getExhibiciones()) {
            if (exhibicion.getCapacidadMaxima() > nuevaCapacidad || exhibicion.getObrasExhibidas().size() > nuevaCapacidad) {
                return false;
            }
        }
        
        if (nuevaCapacidad < sala.cantidadObrasExhibidas()) {
            return false;
            }
        sala.setNombre(nuevoNombre);
        sala.setCapacidadMaxObras(nuevaCapacidad);
        return true;
    }

   // Eliminar sala solamente si no tiene exhibiciones
    public boolean eliminarSala(String id) {
        Sala sala = mapaSalas.get(id);
        if (sala == null || !sala.getExhibiciones().isEmpty()) {
            return false;
        }
        mapaSalas.remove(id);
        return true;
    }
    
   // Sobrecarga: buscar salas por capacidad mínima
    public ArrayList<Sala> buscarSala(int capacidadMinima) {
        ArrayList<Sala> resultado = new ArrayList<>();
        for (Sala sala : mapaSalas.values()) {
            if (sala.getCapacidadMaxObras() >= capacidadMinima) {
                resultado.add(sala);
            }
        }
        return resultado;
    }
}
