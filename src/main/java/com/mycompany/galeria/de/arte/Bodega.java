package com.mycompany.galeria.de.arte;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bodega {

    private Map<String, Obra> inventario; // La clave es el ID de la obra.

    public Bodega() {
        this.inventario = new HashMap<>();
    }

    // METODOS :
    
    public Map<String, Obra> getInventario() {
        return inventario;
    }
    
    public void insertarElemento(String id, Obra obra) {
        //En caso de que la llave este repitiendose se consultara si desea reescribir o no
        if (inventario.containsKey(id)) {
            System.out.println("ADVERTENCIA: Ya existe una obra registrada con el ID " + id);
            System.out.print("¿Desea sobrescribirla de todas maneras? (Si/No): ");
            
            Scanner scanner = new Scanner(System.in);
            String respuesta = scanner.nextLine().trim().toUpperCase(); 

            if (respuesta.equals("Si")) {
                inventario.put(id, obra);
                System.out.println("La obra ha sido sobrescrita.");
            } else {
                System.out.println("Operación cancelada. La obra original se mantiene intacta.");
            }
            
        } else {
            // Si el ID no existe, se inserta normal
            inventario.put(id, obra);
            System.out.println("Obra agregada exitosamente.");
        }
    }
    
    public void listarElementos() {
        //Si es que esta vacia
        if (inventario.isEmpty()) {
            System.out.println("La bodega está vacía. No hay obras registradas.");
            return;
        }

        System.out.println("--- Inventario de Obras en Bodega ---");
        for (Map.Entry<String, Obra> entrada : inventario.entrySet()) {
            System.out.println("ID de Obra: " + entrada.getKey() + " | Datos: " + entrada.getValue());
        }
    }
    
    public void editarElemento(String id, Obra obraModificada) {
    // Verificamos que exista antes de intentar modificar
        if (inventario.containsKey(id)) {
            inventario.replace(id, obraModificada);
            System.out.println("La obra con ID '" + id + "' ha sido actualizada con éxito.");
        } else {
            System.out.println("Error: No se puede editar. La obra con ID '" + id + "' no existe.");
        }
    }
    
    public void eliminarElemento(String id) {
    // El método remove devuelve el objeto si lo eliminó, o null si no existía
        if (inventario.remove(id) != null) {
            System.out.println("La obra con ID '" + id + "' ha sido eliminada permanentemente de la bodega.");
        } else {
            System.out.println("Error: No se encontró ninguna obra con el ID '" + id + "' para eliminar.");
        }
    }
    
    public void buscarElemento(String id) {
    // El método get devuelve la Obra asociada al ID, o null si no la encuentra
        Obra obraEncontrada = inventario.get(id);

        if (obraEncontrada != null) {
            System.out.println("--- OBRA ENCONTRADA ---");
            System.out.println("ID: " + id + " | " + obraEncontrada.toString());
        } else {
            System.out.println("Error: La obra con ID '" + id + "' no se encuentra en el inventario.");
        }
    }
    
}