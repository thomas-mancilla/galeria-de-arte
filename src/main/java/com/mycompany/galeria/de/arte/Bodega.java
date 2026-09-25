package com.mycompany.galeria.de.arte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bodega {

    private Map<EstadoObra, ArrayList<Obra>> inventarioPorEstado;

    public Bodega() {
        this.inventarioPorEstado = new HashMap<>();
        for (EstadoObra estado : EstadoObra.values()) {
            this.inventarioPorEstado.put(estado, new ArrayList<>());
        }
    }

    //Método para agregar una obra a la bodega 
    public void agregarObra(Obra obra, EstadoObra estado) {
        this.inventarioPorEstado.get(estado).add(obra);
    }

    //Método para obtener las listas de la bodega
    public ArrayList<Obra> obtenerObrasPorEstado(EstadoObra estado) {
        return new ArrayList<>(this.inventarioPorEstado.get(estado));
    }

    //Método para eliminar una obra directamente
    public boolean eliminarObra(Obra obra, EstadoObra estadoActual) {
        return this.inventarioPorEstado.get(estadoActual).remove(obra);
    }
    
    public ArrayList<Obra> buscarObras(EstadoObra estado) {
    return inventarioPorEstado.get(estado);
}

    public ArrayList<Obra> buscarObras(String titulo) {
        ArrayList<Obra> resultado = new ArrayList<>();

        for (ArrayList<Obra> obras : inventarioPorEstado.values()) {
            for (Obra obra : obras) {
                if (obra.getTitulo().equalsIgnoreCase(titulo)) {
                    resultado.add(obra);
                }
            }
        }

        return resultado;
    }
    
}