package com.mycompany.galeria.de.arte;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Galeria {
    private Map<String, Sala> mapaSalas;
    private Bodega bodega;

    public Galeria() {
        mapaSalas = new HashMap<>(); //MAPA DE SALAS CONTIENE: SALAS
        this.bodega = new Bodega();
    } 
    
    //Getters
    
    public Map<String, Sala> getMapaSalas() {
        return mapaSalas;
    }

    public Bodega getBodega() {
        return this.bodega;
    }

//METODOS DE SALA:

    public void insertarSala(String id, Sala sala) {
        if (mapaSalas.containsKey(id)) {
            System.out.println("ADVERTENCIA: Ya existe una sala con el ID '" + id + "'.");
            System.out.print("¿Desea sobrescribirla? (Si/No): ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().trim().equalsIgnoreCase("Si")) {
                mapaSalas.put(id, sala);
                System.out.println("Sala sobrescrita con éxito.");
            }
        } else {
            mapaSalas.put(id, sala);
            System.out.println("Sala agregada exitosamente.");
        }
    }

    public void listarSalas() {
        if (mapaSalas.isEmpty()) {
            System.out.println("No hay salas registradas en la Galería.");
            return;
        }
        System.out.println("--- LISTADO DE SALAS ---");
        for (Map.Entry<String, Sala> entrada : mapaSalas.entrySet()) {
            System.out.println("ID Sala: " + entrada.getKey() + " | Datos: " + entrada.getValue());
        }
    }
    
    public void editarSala(String id, Sala salaModificada) {
        if (mapaSalas.containsKey(id)) {
            mapaSalas.replace(id, salaModificada);
            System.out.println("La sala ha sido actualizada.");
        } else {
            System.out.println("Error: No se puede editar. La sala con ID '" + id + "' no existe.");
        }
    }

    public void eliminarSala(String id) {
        if (mapaSalas.remove(id) != null) {
            System.out.println("La sala con ID '" + id + "' ha sido eliminada.");
        } else {
            System.out.println("Error: No se encontró la sala para eliminar.");
        }
    }
    
    public void buscarSala(String id) {
        Sala salaEncontrada = mapaSalas.get(id);
        if (salaEncontrada != null) {
            System.out.println("--- SALA ENCONTRADA ---");
            System.out.println("ID: " + id + " | " + salaEncontrada);
        } else {
            System.out.println("Error: La sala con ID '" + id + "' no existe.");
        }
    }
}