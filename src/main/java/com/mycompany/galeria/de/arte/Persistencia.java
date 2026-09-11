package com.mycompany.galeria.de.arte;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Persistencia {

    public boolean guardarSalas(Galeria galeria) {
        Properties datos = new Properties();
        int indice = 0;

        for (Map.Entry<String, Sala> entrada : galeria.getMapaSalas().entrySet()) {

            Sala sala = entrada.getValue();
            String prefijo = "sala." + indice + ".";

            datos.setProperty(prefijo + "id", entrada.getKey());
            datos.setProperty(prefijo + "nombre", sala.getNombre());
            datos.setProperty(prefijo + "capacidad", String.valueOf(sala.getCapacidadMaxObras()));
            indice++;
        }

        datos.setProperty("cantidad", String.valueOf(indice));
        Path archivo = Paths.get("salas.txt");
        Path temporal = Paths.get("salas.tmp");

        try {
            try (Writer escritor = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                datos.store(escritor, "Salas de la galeria");
            }
            Files.move(temporal, archivo,StandardCopyOption.REPLACE_EXISTING);
            return true;

        }
        catch (IOException e) {
            return false;
        }
    }

    public boolean cargarSalas(Galeria galeria) {
        Path archivo = Paths.get("salas.txt");
        // En la primera ejecución todavía no existe el archivo.
        if (Files.notExists(archivo)) {
            return true;
        }

        Properties datos = new Properties();
        Map<String, Sala> salasCargadas = new HashMap<>();

        try {
            try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
                datos.load(lector);
            }

            int cantidad = Integer.parseInt(datos.getProperty("cantidad"));
            if (cantidad < 0) {
                return false;
            }

            for (int i = 0; i < cantidad; i++) {
                String prefijo = "sala." + i + ".";
                String id = datos.getProperty(prefijo + "id");
                String nombre = datos.getProperty(prefijo + "nombre");
                String textoCapacidad = datos.getProperty(prefijo + "capacidad");

                if (id == null || id.trim().isEmpty() || nombre == null || nombre.trim().isEmpty() || textoCapacidad == null) {
                    return false;
                }
                int capacidad = Integer.parseInt(textoCapacidad);
                if (capacidad <= 0 || salasCargadas.containsKey(id)) {
                    return false;
                }
                Sala sala = new Sala(id, nombre, capacidad);
                salasCargadas.put(id, sala);
            }
            // Solo cambia la galería si todo el archivo es válido.
            galeria.getMapaSalas().clear();
            galeria.getMapaSalas().putAll(salasCargadas);

            return true;

        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }
}
