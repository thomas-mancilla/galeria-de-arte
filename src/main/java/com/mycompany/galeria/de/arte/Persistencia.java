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
            datos.setProperty(prefijo + "exhibiciones.cantidad", String.valueOf(sala.getExhibiciones().size()));
       
            int indiceExhibicion = 0;

            for (Exhibicion exhibicion : sala.getExhibiciones()) {
                String prefijoExhibicion = prefijo + "exhibicion." + indiceExhibicion + ".";
                datos.setProperty(prefijoExhibicion + "tematica",    exhibicion.getTematica());

                datos.setProperty(prefijoExhibicion + "capacidad", String.valueOf(exhibicion.getCapacidadMaxima()));
                datos.setProperty(prefijoExhibicion + "obras.cantidad", String.valueOf(exhibicion.getObrasExhibidas().size()));

                int indiceObra = 0;
                for (Obra obra : exhibicion.getObrasExhibidas()) {
                    String prefijoObra = prefijoExhibicion + "obra." + indiceObra + ".";
                    guardarDatosObra(datos, prefijoObra, obra);

                    indiceObra++;
                }
                indiceExhibicion++;
            }
                indice++;
        }

        datos.setProperty("cantidad", String.valueOf(indice));
        for (EstadoObra estado : EstadoObra.values()) {
            String prefijoEstado = "bodega." + estado.name() + ".";

            datos.setProperty(prefijoEstado + "cantidad", String.valueOf( galeria.getBodega().obtenerObrasPorEstado(estado).size()));
            
            int indiceObra = 0;
            for (Obra obra : galeria.getBodega().obtenerObrasPorEstado(estado)) {
                String prefijoObra = prefijoEstado + "obra." + indiceObra + ".";
                guardarDatosObra(datos, prefijoObra, obra);
                indiceObra++;
            }
        }
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
                
                int cantidadExhibiciones = Integer.parseInt(datos.getProperty(prefijo + "exhibiciones.cantidad", "0"));
                if (cantidadExhibiciones < 0) {
                    return false;
                }

                for (int j = 0; j < cantidadExhibiciones; j++) {
                    String prefijoExhibicion = prefijo + "exhibicion." + j + ".";
                    String tematica = datos.getProperty(prefijoExhibicion + "tematica");
                    int capacidadExhibicion = Integer.parseInt( datos.getProperty(prefijoExhibicion + "capacidad"));
                    Exhibicion exhibicion = new Exhibicion(tematica, capacidadExhibicion);
                    int cantidadObras = Integer.parseInt( datos.getProperty(prefijoExhibicion + "obras.cantidad", "0"));

                    if (cantidadObras < 0 || cantidadObras > capacidadExhibicion) {
                        return false;
                    }

                    for (int k = 0; k < cantidadObras; k++) {
                        String prefijoObra = prefijoExhibicion + "obra." + k + ".";
                        Obra obra = cargarDatosObra(datos, prefijoObra);
                        if (obra.getEstado() != EstadoObra.EN_EXHIBICION) {
                            return false;
                        }
                        exhibicion.getObrasExhibidas().add(obra);
                    }

                    if (!sala.agregarExhibicion(exhibicion)) {
                        return false;
                    }
                }
                salasCargadas.put(id, sala);
            }
            Bodega bodegaCargada = new Bodega();

            for (EstadoObra estado : EstadoObra.values()) {
                String prefijoEstado = "bodega." + estado.name() + ".";
                int cantidadObras = Integer.parseInt(datos.getProperty(prefijoEstado + "cantidad", "0"));
                if (cantidadObras < 0) {
                    return false;
                }

                for (int k = 0; k < cantidadObras; k++) {
                    String prefijoObra = prefijoEstado + "obra." + k + ".";
                    Obra obra = cargarDatosObra(datos, prefijoObra);
                    if (obra.getEstado() != estado) {
                        return false;
                    }
                    bodegaCargada.agregarObra(obra, estado);
                }
            }

            galeria.getMapaSalas().clear();
            galeria.getMapaSalas().putAll(salasCargadas);
            for (EstadoObra estado : EstadoObra.values()) {
                galeria.getBodega().obtenerObrasPorEstado(estado).clear();
                galeria.getBodega().obtenerObrasPorEstado(estado).addAll( bodegaCargada.obtenerObrasPorEstado(estado));
            }
            return true;

        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }
    private void guardarDatosObra(Properties datos, String prefijo, Obra obra) {
        
        datos.setProperty(prefijo + "titulo", obra.getTitulo());
        datos.setProperty(prefijo + "artista.nombre", obra.getAutor().getNombre());
        datos.setProperty(prefijo + "artista.nacionalidad",obra.getAutor().getNacionalidad());
        datos.setProperty(prefijo + "precio", String.valueOf(obra.getPrecio()));
        datos.setProperty(prefijo + "anio", String.valueOf(obra.getAnioCreacion()));
        datos.setProperty(prefijo + "estado",obra.getEstado().name());
    }
    private Obra cargarDatosObra(Properties datos, String prefijo) {
        String titulo = datos.getProperty(prefijo + "titulo");
        String nombre = datos.getProperty(prefijo + "artista.nombre");
        String nacionalidad = datos.getProperty(prefijo + "artista.nacionalidad");
        String textoEstado = datos.getProperty(prefijo + "estado");

        if (titulo == null || titulo.trim().isEmpty() || nombre == null || nombre.trim().isEmpty() || nacionalidad == null || textoEstado == null) {
            throw new IllegalArgumentException("Datos incompletos de la obra.");
        }

        int precio = Integer.parseInt( datos.getProperty(prefijo + "precio"));
        int anio = Integer.parseInt(datos.getProperty(prefijo + "anio"));

        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        EstadoObra estado = EstadoObra.valueOf(textoEstado);
        Artista artista = new Artista(nombre, nacionalidad);
        return new Obra(artista, titulo, estado, precio, anio);
    }
}
