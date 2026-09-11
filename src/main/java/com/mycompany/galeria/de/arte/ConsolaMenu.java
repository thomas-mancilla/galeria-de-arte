package com.mycompany.galeria.de.arte;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsolaMenu {

    private Scanner scanner;
    private Galeria galeria;
    
    public ConsolaMenu(Galeria galeria) {
        this.galeria = galeria;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\nGALERÍA DE ARTE");
            System.out.println("----------------");
            System.out.println("[1] Obras");
            System.out.println("[2] Salas");
            System.out.println("[3] Clientes");
            System.out.println("[4] Ventas");
            System.out.println("[5] Préstamos");
            System.out.println("[0] Salir");

            System.out.print("\nSeleccione: ");
            opcion = leerEntero();
            System.out.println(); 

            switch (opcion) {

                case 1:
                    mostrarMenuObras();
                    break;

                case 2:
                    mostrarMenuSalas();
                    break;

                case 3:
                    System.out.println("Gestionar clientes");
                    break;

                case 4:
                    System.out.println("Gestionar ventas");
                    break;

                case 5:
                    System.out.println("Gestionar préstamos");
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    private void mostrarMenuObras() {
        int opcionObra;
        do {
            System.out.println("\nMENÚ OBRAS");
            System.out.println("----------");
            System.out.println("[1] Ingresar nueva obra (Por defecto entra a Bodega)");
            System.out.println("[2] Ver inventario en Bodega");
            System.out.println("[3] Ver obras en Exhibición");
            System.out.println("[4] Trasladar obra (Bodega <-> Exhibición)");
            System.out.println("[5] Editar datos de la obra");
            System.out.println("[6] Dar de baja / Eliminar obra");
            System.out.println("[0] Volver");

            System.out.print("\nSeleccione: ");
            opcionObra = leerEntero();
            System.out.println(); 

            switch (opcionObra) {
                case 1: {
                    System.out.println("--- INGRESAR NUEVA OBRA ---");
                    System.out.print("Título de la obra: ");
                    String titulo = scanner.nextLine().trim();

                    System.out.print("Precio: ");
                    int precio = leerEntero();

                    System.out.print("Año de creación: ");
                    int anio = leerEntero();

                    System.out.print("Nombre del artista: ");
                    String nombreArtista = scanner.nextLine().trim();

                    // Creación de los objetos basados en tus clases
                    Artista autor = new Artista(nombreArtista, "Desconocida"); 
                    Obra nuevaObra = new Obra(autor, titulo, EstadoObra.GUARDADA, precio, anio);

                    // Acceso a la bodega anidada
                    galeria.getBodega().agregarObra(nuevaObra, EstadoObra.GUARDADA);
                    System.out.println("\nObra ingresada exitosamente a la Bodega (Estado: GUARDADA).");
                    break;
                }
                case 2: {
                    System.out.println("--- INVENTARIO EN BODEGA ---");
                    // Traemos la lista directamete desde el mapa de la bodega
                    ArrayList<Obra> obrasGuardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                    if (obrasGuardadas == null || obrasGuardadas.isEmpty()) {
                        System.out.println("La bodega está vacía actualmente.");
                    } else {
                        for (int i = 0; i < obrasGuardadas.size(); i++) {
                            System.out.println("- " + obrasGuardadas.get(i).getTitulo());
                        }
                    }
                    break;
                }
                case 3: {
                    System.out.println("--- OBRAS EN EXHIBICIÓN ---");
                    ArrayList<Sala> salas = galeria.listarSalas();
                    boolean hayObras = false;

                    // Recorremos las colecciones anidadas de Sala -> Exhibiciones -> Obras
                    for (Sala sala : salas) {
                        for (Exhibicion exhibicion : sala.getExhibiciones()) {
                            ArrayList<Obra> obras = exhibicion.getObrasExhibidas();

                            if (!obras.isEmpty()) {
                                hayObras = true;
                                System.out.println("\nSala: " + sala.getNombre() + " | Temática: " + exhibicion.getTematica());
                                for (Obra obra : obras) {
                                    System.out.println("  -> " + obra.getTitulo());
                                }
                            }
                        }
                    }

                    if (!hayObras) {
                        System.out.println("No hay obras en exhibición en ninguna sala en este momento.");
                    }
                    break;
                }
                case 4: {
                    System.out.println("--- TRASLADAR OBRA ---");
                    System.out.println("1. De Bodega a Exhibición");
                    System.out.println("2. De Exhibición a Bodega");
                    System.out.print("Seleccione el tipo de traslado: ");
                    int tipoTraslado = leerEntero();

                    if (tipoTraslado == 1) {
                        // Traslado: BODEGA -> EXHIBICIÓN
                        ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                        if (guardadas == null || guardadas.isEmpty()) {
                            System.out.println("No hay obras en Bodega para trasladar.");
                            break;
                        }

                        // 1. Mostrar y seleccionar la obra de la bodega
                        System.out.println("\nObras en bodega:");
                        for (int i = 0; i < guardadas.size(); i++) {
                            System.out.println("[" + i + "] " + guardadas.get(i).getTitulo());
                        }
                        System.out.print("Seleccione el número de la obra a trasladar: ");
                        int idxObra = leerEntero();

                        if (idxObra < 0 || idxObra >= guardadas.size()) {
                            System.out.println("Opción inválida.");
                            break;
                        }
                        Obra obraATrasladar = guardadas.get(idxObra);

                        // 2. Seleccionar la Sala
                        ArrayList<Sala> salas = galeria.listarSalas();
                        if (salas.isEmpty()) {
                            System.out.println("No hay salas registradas. Cree una sala primero.");
                            break;
                        }
                        System.out.println("\nSalas disponibles:");
                        for (int i = 0; i < salas.size(); i++) {
                            System.out.println("[" + i + "] " + salas.get(i).getNombre());
                        }
                        System.out.print("Seleccione el número de la sala: ");
                        int idxSala = leerEntero();

                        if (idxSala < 0 || idxSala >= salas.size()) {
                            System.out.println("Opción inválida.");
                            break;
                        }
                        Sala salaSeleccionada = salas.get(idxSala);

                        // 3. Seleccionar la Exhibición dentro de la sala
                        ArrayList<Exhibicion> exhibiciones = salaSeleccionada.getExhibiciones();
                        if (exhibiciones.isEmpty()) {
                            System.out.println("Esta sala no tiene exhibiciones. Cree una primero.");
                            break;
                        }
                        System.out.println("\nExhibiciones en " + salaSeleccionada.getNombre() + ":");
                        for (int i = 0; i < exhibiciones.size(); i++) {
                            System.out.println("[" + i + "] Temática: " + exhibiciones.get(i).getTematica());
                        }
                        System.out.print("Seleccione la exhibición destino: ");
                        int idxExh = leerEntero();

                        if (idxExh < 0 || idxExh >= exhibiciones.size()) {
                            System.out.println("Opción inválida.");
                            break;
                        }
                        Exhibicion exhibicionDestino = exhibiciones.get(idxExh);

                        // 4. Efectuar el traslado
                        if (exhibicionDestino.getObrasExhibidas().size() >= exhibicionDestino.getCapacidadMaxima()) {
                            System.out.println("Error: La exhibición ya alcanzó su capacidad máxima.");
                        } else {
                            guardadas.remove(obraATrasladar); // La sacamos de la lista de bodega
                            obraATrasladar.setEstado(EstadoObra.EN_EXHIBICION); // Actualizamos su estado
                            exhibicionDestino.getObrasExhibidas().add(obraATrasladar); // La añadimos a exhibición
                            System.out.println("\n¡Traslado exitoso!");
                        }

                    } else if (tipoTraslado == 2) {
                        // Traslado: EXHIBICIÓN -> BODEGA
                        System.out.print("\nIngrese el título de la obra que desea regresar a Bodega: ");
                        String tituloBuscar = scanner.nextLine().trim();
                        boolean trasladada = false;

                        // Recorrer todas las salas y exhibiciones buscando la obra
                        for (Sala sala : galeria.listarSalas()) {
                            for (Exhibicion exhibicion : sala.getExhibiciones()) {
                                ArrayList<Obra> obras = exhibicion.getObrasExhibidas();

                                for (int i = 0; i < obras.size(); i++) {
                                    Obra obra = obras.get(i);
                                    if (obra.getTitulo().equalsIgnoreCase(tituloBuscar)) {
                                        // Efectuar traslado inverso
                                        obras.remove(i);
                                        obra.setEstado(EstadoObra.GUARDADA);
                                        galeria.getBodega().agregarObra(obra, EstadoObra.GUARDADA);

                                        trasladada = true;
                                        System.out.println("\nObra '" + obra.getTitulo() + "' devuelta a la bodega con éxito.");
                                        break; // Romper el ciclo de obras
                                    }
                                }
                                if (trasladada) break; // Romper el ciclo de exhibiciones
                            }
                            if (trasladada) break; // Romper el ciclo de salas
                        }

                        if (!trasladada) {
                            System.out.println("\nNo se encontró ninguna obra con ese título en exhibición.");
                        }
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;
                }
                case 5: {
                    System.out.println("--- EDITAR DATOS DE LA OBRA ---");
                    System.out.print("Ingrese el título de la obra que desea editar: ");
                    String tituloEditar = scanner.nextLine().trim();
                    Obra obraAEditar = null;

                    // 1. Buscar en la Bodega
                    ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
                    if (guardadas != null) {
                        for (Obra o : guardadas) {
                            if (o.getTitulo().equalsIgnoreCase(tituloEditar)) {
                                obraAEditar = o;
                                break;
                            }
                        }
                    }

                    // 2. Si no está en bodega, buscar en las Exhibiciones
                    if (obraAEditar == null) {
                        for (Sala sala : galeria.listarSalas()) {
                            for (Exhibicion exhibicion : sala.getExhibiciones()) {
                                for (Obra o : exhibicion.getObrasExhibidas()) {
                                    if (o.getTitulo().equalsIgnoreCase(tituloEditar)) {
                                        obraAEditar = o;
                                        break;
                                    }
                                }
                                if (obraAEditar != null) break;
                            }
                            if (obraAEditar != null) break;
                        }
                    }

                    // 3. Proceso de edición (Si se encontró la obra)
                    if (obraAEditar == null) {
                        System.out.println("Obra no encontrada en el sistema.");
                    } else {
                        System.out.println("\nEditando obra: " + obraAEditar.getTitulo());

                        System.out.print("Nuevo título (Presione Enter para no cambiar): ");
                        String nuevoTitulo = scanner.nextLine().trim();
                        if (!nuevoTitulo.isEmpty()) {
                            obraAEditar.setTitulo(nuevoTitulo);
                        }

                        System.out.print("Nuevo precio (-1 para no cambiar): ");
                        int nuevoPrecio = leerEntero();
                        if (nuevoPrecio != -1) {
                            obraAEditar.setPrecio(nuevoPrecio);
                        }

                        System.out.print("Nuevo año de creación (-1 para no cambiar): ");
                        int nuevoAnio = leerEntero();
                        if (nuevoAnio != -1) {
                            obraAEditar.setAnioCreacion(nuevoAnio);
                        }

                        System.out.println("\n¡Datos actualizados exitosamente!");
                    }
                    break;
                }
                case 6: {
                    System.out.println("--- DAR DE BAJA / ELIMINAR OBRA ---");
                    System.out.print("Ingrese el título de la obra a eliminar: ");
                    String tituloEliminar = scanner.nextLine().trim();
                    boolean eliminada = false;

                    // 1. Intentar eliminar de la Bodega
                    ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
                    if (guardadas != null) {
                        for (int i = 0; i < guardadas.size(); i++) {
                            if (guardadas.get(i).getTitulo().equalsIgnoreCase(tituloEliminar)) {
                                guardadas.remove(i);
                                eliminada = true;
                                break;
                            }
                        }
                    }

                    // 2. Si no estaba en bodega, intentar eliminar de Exhibiciones
                    if (!eliminada) {
                        for (Sala sala : galeria.listarSalas()) {
                            for (Exhibicion exhibicion : sala.getExhibiciones()) {
                                ArrayList<Obra> obras = exhibicion.getObrasExhibidas();
                                for (int i = 0; i < obras.size(); i++) {
                                    if (obras.get(i).getTitulo().equalsIgnoreCase(tituloEliminar)) {
                                        obras.remove(i);
                                        eliminada = true;
                                        break;
                                    }
                                }
                                if (eliminada) break;
                            }
                            if (eliminada) break;
                        }
                    }

                    if (eliminada) {
                        System.out.println("\nLa obra ha sido eliminada del sistema correctamente.");
                    } else {
                        System.out.println("\nNo se pudo eliminar: Obra no encontrada.");
                    }
                    break;
                }
                case 0: {
                    System.out.println("Volviendo al menú principal...");
                    break;
                }
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcionObra != 0);
    }
    
    private void mostrarMenuSalas() {
        int opcion;
        do {
            System.out.println("\nSALAS");
            System.out.println("[1] Insertar sala");
            System.out.println("[2] Listar salas");
            System.out.println("[3] Buscar por ID");
            System.out.println("[4] Buscar por capacidad mínima");
            System.out.println("[5] Editar sala");
            System.out.println("[6] Eliminar sala");
            System.out.println("[0] Volver");
            
            System.out.print("\nSeleccione: ");
            opcion = leerEntero();
            System.out.println(); 

            switch (opcion) {
                case 1: {
                    System.out.print("ID de la sala: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine().trim();

                    System.out.print("Capacidad máxima de obras: ");
                    int capacidad = leerEntero();
                    System.out.println(); 

                    Sala sala = new Sala(id, nombre, capacidad);

                    if (galeria.insertarSala(id, sala)) {
                        System.out.println("Sala agregada correctamente.");
                    } else {
                        System.out.println("No se pudo agregar: ID repetido o datos inválidos.");
                    }
                    break;
                }

                case 2: {
                    mostrarSalas(galeria.listarSalas());
                    break;
                }

                case 3: {
                    System.out.print("ID de la sala: ");
                    String id = scanner.nextLine().trim();
                    System.out.println(); 
                    Sala sala = galeria.buscarSala(id);

                    if (sala == null) {
                        System.out.println("No existe una sala con ese ID.");
                    } else {
                        mostrarSala(sala);
                    }
                    break;
                }

                case 4: {
                    System.out.print("Capacidad mínima: ");
                    int capacidad = leerEntero();
                    System.out.println(); 

                    if (capacidad <= 0) {
                        System.out.println("La capacidad debe ser positiva.");
                    } else {
                        mostrarSalas(galeria.buscarSala(capacidad));
                    }
                    break;
                }

                case 5: {
                    System.out.print("ID de la sala a editar: ");
                    String id = scanner.nextLine().trim();
                    if (galeria.buscarSala(id) == null) {
                        System.out.println("\nNo existe una sala con ese ID.");
                        break;
                    }
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Nueva capacidad: ");
                    int capacidad = leerEntero();
                    System.out.println(); 

                    if (galeria.editarSala(id, nombre, capacidad)) {
                        System.out.println("Sala modificada correctamente.");
                    } else {
                        System.out.println("Datos inválidos o capacidad insuficiente para las exhibiciones.");
                    }
                    break;
                }

                case 6: {
                    System.out.print("ID de la sala a eliminar: ");
                    String id = scanner.nextLine().trim();
                    System.out.println(); 
                    
                    if (galeria.eliminarSala(id)) {
                        System.out.println("Sala eliminada correctamente.");
                    } else {
                        System.out.println("La sala no existe o todavía tiene exhibiciones.");
                    }
                    break;
                }

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private void mostrarSala(Sala sala) {
        System.out.println("ID: " + sala.getNumero() + " | Nombre: " + sala.getNombre() + " | Capacidad: " + sala.getCapacidadMaxObras());
    }

    private void mostrarSalas(ArrayList<Sala> salas) {
        if (salas.isEmpty()) {
            System.out.println("No hay salas para mostrar.");
            return;
        }
        for (Sala sala : salas) {
            mostrarSala(sala);
        }
    }   
}