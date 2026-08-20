package com.mycompany.galeria.de.arte;

import java.util.HashMap;
import java.util.Map;

public class Bodega {

    private Map<String, Obra> inventario; // La clave es el ID de la obra.

    public Bodega() {
        this.inventario = new HashMap<>();
    }

    public Map<String, Obra> getInventario() {
        return inventario;
    }

    public void setInventario(Map<String, Obra> inventario) {
        this.inventario = inventario;
    }
}