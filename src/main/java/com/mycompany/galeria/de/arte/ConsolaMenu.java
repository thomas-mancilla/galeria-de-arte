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
                    mostrarMenuClientes();
                    break;

                case 4:
                    mostrarMenuVentas();
                    break;

                case 5:
                    mostrarMenuPrestamos();
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
            System.out.println("[1] Ingresar nueva obra (Por defecto entra a Bodega)");
            System.out.println("[2] Ver inventario en Bodega");
            System.out.println("[3] Ver obras en Exhibición");
            System.out.println("[4] Trasladar obra (Bodega <-> Exhibición)");
            System.out.println("[5] Editar datos de la obra");
            System.out.println("[6] Dar de baja / Eliminar obra");
            System.out.println("[7] Buscar obra por estado");
            System.out.println("[8] Buscar obra por título");
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
                    if (titulo.isEmpty() || nombreArtista.isEmpty()) {
                        System.out.println("El título y el nombre del artista no pueden estar vacíos.");
                        break;
                    }
                    if (precio < 0) {
                        System.out.println("El precio no puede ser negativo.");
                        break;
                    }
                    
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

                    // Recorremos las colecciones anidadas de Sala
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
                        // Traslado: BODEGA A EXHIBICIÓN
                        ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                        if (guardadas == null || guardadas.isEmpty()) {
                            System.out.println("No hay obras en Bodega para trasladar.");
                            break;
                        }

                        //Mostrar y seleccionar la obra de la bodega
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

                        //Seleccionar la Sala
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

                        //Seleccionar la Exhibición dentro de la sala
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

                        //Efectuar el traslado
                        if (exhibicionDestino.getObrasExhibidas().size() >= exhibicionDestino.getCapacidadMaxima() || salaSeleccionada.cantidadObrasExhibidas() >= salaSeleccionada.getCapacidadMaxObras()) {
                            System.out.println("Error: La exhibición o la sala ya alcanzó su capacidad máxima.");
                        } else {
                            guardadas.remove(obraATrasladar); // La sacamos de la lista de bodega
                            obraATrasladar.setEstado(EstadoObra.EN_EXHIBICION); // Actualizamos su estado
                            exhibicionDestino.getObrasExhibidas().add(obraATrasladar); // La añadimos a exhibición
                            System.out.println("\n¡Traslado exitoso!");
                        }

                    } else if (tipoTraslado == 2) {
                        // Traslado: EXHIBICIÓN A BODEGA
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

                    //Buscar en la Bodega
                    ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
                    if (guardadas != null) {
                        for (Obra o : guardadas) {
                            if (o.getTitulo().equalsIgnoreCase(tituloEditar)) {
                                obraAEditar = o;
                                break;
                            }
                        }
                    }

                    //Si no está en bodega, buscar en las Exhibiciones
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

                    //Proceso de edición (Si se encontró la obra)
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
                        while (nuevoPrecio < -1) {
                            System.out.print("Ingrese un precio igual o mayor que 0, o -1 para conservarlo: ");
                            nuevoPrecio = leerEntero();
                        }
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

                    //Intentar eliminar de la Bodega
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

                    //Si no estaba en bodega, intentar eliminar de Exhibiciones
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
                
                case 7: {
                    System.out.println("--- BUSCAR OBRAS POR ESTADO ---");

                    System.out.println("Estados disponibles:");
                    for (EstadoObra estado : EstadoObra.values()) {
                        System.out.println("- " + estado);
                    }

                    System.out.print("Ingrese el estado: ");
                    String estadoIngresado = scanner.nextLine().trim().toUpperCase();

                    try {
                        EstadoObra estado = EstadoObra.valueOf(estadoIngresado);

                        ArrayList<Obra> obras =
                                galeria.getBodega().buscarObras(estado);

                        if (obras == null || obras.isEmpty()) {
                            System.out.println("No hay obras con ese estado.");
                        } else {
                            System.out.println("\nObras encontradas:");

                            for (Obra obra : obras) {
                                System.out.println("- " + obra.getTitulo());
                            }
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("Estado no válido.");
                    }

                    break;
                }

                case 8: {
                    System.out.println("--- BUSCAR OBRA POR TÍTULO ---");

                    System.out.print("Ingrese el título de la obra: ");
                    String tituloBuscar = scanner.nextLine().trim();

                    ArrayList<Obra> resultados =
                            galeria.getBodega().buscarObras(tituloBuscar);

                    if (resultados == null || resultados.isEmpty()) {
                        System.out.println("No se encontró ninguna obra con ese título.");
                    } else {
                        System.out.println("\nObras encontradas:");

                        for (Obra obra : resultados) {
                            System.out.println("- " + obra.getTitulo());
                        }
                    }

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
            System.out.println("[7] Gestionar exhibiciones de una sala");
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
                case 7: {
                    System.out.print("ID de la sala: ");
                    Sala sala = galeria.buscarSala(scanner.nextLine().trim());
                    if (sala == null) {
                        System.out.println("No existe una sala con ese ID.");
                    } else {
                        mostrarMenuExhibiciones(sala);
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

    private void mostrarMenuVentas() {
        int opcion;
        do {
            System.out.println("\nMENÚ VENTAS");
            System.out.println("-----------");
            System.out.println("[1] Realizar una venta");
            System.out.println("[2] Mostrar ventas de la galería");
            System.out.println("[0] Volver");
            System.out.print("\nSeleccione: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: {
                    System.out.println("--- REALIZAR VENTA ---");
                    // Trae las obras que están disponibles en Bodega
                    ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                    if (disponibles == null || disponibles.isEmpty()) {
                        System.out.println("No hay obras disponibles para vender en la Bodega.");
                        break;
                    }

                    System.out.println("Obras disponibles:");
                    for (int i = 0; i < disponibles.size(); i++) {
                        System.out.println("[" + i + "] " + disponibles.get(i).getTitulo() + " - $" + disponibles.get(i).getPrecio());
                    }

                    System.out.print("\nSeleccione el número de la obra a vender: ");
                    int idx = leerEntero();

                    if (idx < 0 || idx >= disponibles.size()) {
                        System.out.println("Opción inválida.");
                        break;
                    }
                    Obra obraSeleccionada = disponibles.get(idx);

                    // Recopilación de datos del Cliente 
                    System.out.print("Ingrese el RUT del cliente: ");
                    String rut = scanner.nextLine().trim();

                    System.out.print("Ingrese el nombre del cliente: ");
                    String nombre = scanner.nextLine().trim();

                    System.out.print("Ingrese el teléfono del cliente: ");
                    String telefono = scanner.nextLine().trim();

                    System.out.print("Ingrese el correo electrónico del cliente: ");
                    String correo = scanner.nextLine().trim();

                    Cliente cliente = new Cliente(rut, nombre, telefono, correo);

                    // Obtener fecha actual
                    String fechaActual = java.time.LocalDate.now().toString();

                    //Efectuar el traslado en la colección anidada de Bodega
                    disponibles.remove(obraSeleccionada); 
                    obraSeleccionada.setEstado(EstadoObra.VENDIDA);
                    galeria.getBodega().agregarObra(obraSeleccionada, EstadoObra.VENDIDA);

                    //Registrar la venta en la Galería
                    Venta nuevaVenta = new Venta(obraSeleccionada, cliente, fechaActual, obraSeleccionada.getPrecio());
                    galeria.getRegistroVentas().add(nuevaVenta);

                    System.out.println("\n¡Venta registrada exitosamente!");
                    break;
                }
                case 2: {
                    System.out.println("--- HISTORIAL DE VENTAS ---");
                    ArrayList<Venta> ventas = galeria.getRegistroVentas();

                    if (ventas == null || ventas.isEmpty()) {
                        System.out.println("Aún no se han registrado ventas.");
                    } else {
                        for (Venta v : ventas) {
                            System.out.println("Fecha: " + v.getFecha() + 
                                               " | Cliente: " + v.getCliente().getNombre() + " (RUT: " + v.getCliente().getRut() + ")" +
                                               " | Obra: " + v.getObra().getTitulo() + 
                                               " | Monto: $" + v.getPrecioVenta());
                        }
                    }
                    break;
                }
                case 0:
                    System.out.println("Volviendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    private void mostrarMenuClientes() {
        int opcion;
        do {
            System.out.println("\nMENÚ CLIENTES");
            System.out.println("-------------");
            System.out.println("[1] Ver totalidad de clientes (creados por compras)");
            System.out.println("[0] Volver");
            System.out.print("\nSeleccione: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: {
                    System.out.println("--- LISTADO DE CLIENTES HISTÓRICOS ---");
                    // Extraemos las ventas desde la galería
                    ArrayList<Venta> ventas = galeria.getRegistroVentas();

                    if (ventas == null || ventas.isEmpty()) {
                        System.out.println("Aún no hay clientes registrados en el sistema.");
                    } else {
                        // Lista auxiliar para no imprimir al mismo cliente dos veces
                        ArrayList<String> rutsImpresos = new ArrayList<>();

                        for (Venta v : ventas) {
                            Cliente c = v.getCliente();

                            // Si el RUT no está en nuestra lista de control, lo imprimimos
                            if (!rutsImpresos.contains(c.getRut())) {
                                System.out.println("- RUT: " + c.getRut() + 
                                                   " | Nombre: " + c.getNombre() + 
                                                   " | Tel: " + c.getTelefono() + 
                                                   " | Correo: " + c.getCorreo());
                                rutsImpresos.add(c.getRut());
                            }
                        }
                    }
                    break;
                }
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    
    private void mostrarMenuPrestamos() {
        int opcion;
        do {
            System.out.println("\nMENÚ PRÉSTAMOS");
            System.out.println("--------------");
            System.out.println("[1] Pedir préstamo de Obra");
            System.out.println("[2] Devolver préstamo de Obra (Vuelve a Bodega)");
            System.out.println("[3] Ver préstamos activos");
            System.out.println("[4] Ver obras disponibles para préstamo");
            System.out.println("[0] Volver");
            System.out.print("\nSeleccione: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1: {
                    System.out.println("--- PEDIR PRÉSTAMO ---");
                    // Buscamos obras disponibles en la colección anidada de la bodega
                    ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                    if (disponibles == null || disponibles.isEmpty()) {
                        System.out.println("No hay obras disponibles para prestar en la Bodega.");
                        break;
                    }

                    System.out.println("Obras disponibles:");
                    for (int i = 0; i < disponibles.size(); i++) {
                        System.out.println("[" + i + "] " + disponibles.get(i).getTitulo());
                    }

                    System.out.print("\nSeleccione el número de la obra a prestar: ");
                    int idx = leerEntero();

                    if (idx < 0 || idx >= disponibles.size()) {
                        System.out.println("Opción inválida.");
                        break;
                    }
                    Obra obraSeleccionada = disponibles.get(idx);

                    // Instanciar el Cliente
                    System.out.print("Ingrese RUT del cliente: ");
                    String rut = scanner.nextLine().trim();
                    System.out.print("Ingrese Nombre del cliente: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Ingrese Teléfono: ");
                    String telefono = scanner.nextLine().trim();
                    System.out.print("Ingrese Correo: ");
                    String correo = scanner.nextLine().trim();
                    Cliente cliente = new Cliente(rut, nombre, telefono, correo);

                    // Fechas
                    String fechaInicio = java.time.LocalDate.now().toString();
                    System.out.print("Ingrese fecha de devolución estimada (ej. 2026-12-01): ");
                    String fechaDevolucion = scanner.nextLine().trim();

                    //Traslado interno en la Bodega
                    disponibles.remove(obraSeleccionada); 
                    obraSeleccionada.setEstado(EstadoObra.PRESTADA);
                    galeria.getBodega().agregarObra(obraSeleccionada, EstadoObra.PRESTADA);

                    //Crear y guardar el registro
                    Prestamo nuevoPrestamo = new Prestamo(obraSeleccionada, cliente, fechaInicio, fechaDevolucion);
                    galeria.getRegistroPrestamos().add(nuevoPrestamo);

                    System.out.println("\n¡Préstamo registrado exitosamente!");
                    break;
                }
                case 2: {
                    System.out.println("--- DEVOLVER PRÉSTAMO ---");
                    ArrayList<Prestamo> prestamos = galeria.getRegistroPrestamos();

                    if (prestamos == null || prestamos.isEmpty()) {
                        System.out.println("No hay préstamos activos.");
                        break;
                    }

                    System.out.println("Préstamos activos:");
                    for (int i = 0; i < prestamos.size(); i++) {
                        Prestamo p = prestamos.get(i);
                        System.out.println("[" + i + "] Obra: " + p.getObra().getTitulo() +  " | Cliente: " + p.getCliente().getNombre());
                    }

                    System.out.print("\nSeleccione el número del préstamo a devolver: ");
                    int idx = leerEntero();

                    if (idx < 0 || idx >= prestamos.size()) {
                        System.out.println("Opción inválida.");
                        break;
                    }

                    Prestamo prestamoDevuelto = prestamos.get(idx);
                    Obra obraDevuelta = prestamoDevuelto.getObra();

                    //Devolver a la lista GUARDADA
                    galeria.getBodega().obtenerObrasPorEstado(EstadoObra.PRESTADA).remove(obraDevuelta);
                    obraDevuelta.setEstado(EstadoObra.GUARDADA);
                    galeria.getBodega().agregarObra(obraDevuelta, EstadoObra.GUARDADA);

                    //Eliminar el registro del préstamo activo
                    prestamos.remove(idx);
                    System.out.println("\nObra '" + obraDevuelta.getTitulo() + "' devuelta a la Bodega exitosamente.");
                    break;
                }
                case 3: {
                    System.out.println("--- PRÉSTAMOS ACTIVOS ---");
                    ArrayList<Prestamo> prestamos = galeria.getRegistroPrestamos();

                    if (prestamos == null || prestamos.isEmpty()) {
                        System.out.println("No hay préstamos activos en este momento.");
                    } else {
                        for (Prestamo p : prestamos) {
                            System.out.println("- Obra: " + p.getObra().getTitulo() +  " | Cliente: " + p.getCliente().getNombre() +  " | Inicio: " + p.getFechaInicio() +  " | Devolución: " + p.getFechaDevolucion());
                        }
                    }
                    break;
                }
                case 4: {
                    System.out.println("--- OBRAS DISPONIBLES PARA PRÉSTAMO ---");
                    ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);

                    if (disponibles == null || disponibles.isEmpty()) {
                        System.out.println("No hay obras disponibles en Bodega.");
                    } else {
                        for (Obra o : disponibles) {
                            System.out.println("- " + o.getTitulo() + " (Artista: " + o.getAutor().getNombre() + ")");
                        }
                    }
                    break;
                }
                case 0:
                    System.out.println("Volviendo al menú principal...");
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

    private void mostrarMenuExhibiciones(Sala sala) {
        int opcion;
        do {
            System.out.println("\nEXHIBICIONES DE SALA: " + sala.getNombre());
            System.out.println("[1] Agregar exhibición");
            System.out.println("[2] Listar exhibiciones");
            System.out.println("[3] Buscar exhibición por temática");
            System.out.println("[4] Editar exhibición");
            System.out.println("[5] Eliminar exhibición");
            System.out.println("[0] Volver");
            System.out.print("Seleccione: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1: {
                    System.out.print("Temática: ");
                    String tematica = scanner.nextLine().trim();
                    System.out.print("Capacidad máxima de obras: ");
                    int capacidad = leerEntero();
                    boolean agregada = sala.agregarExhibicion(new Exhibicion(tematica, capacidad));
                    System.out.println(agregada ? "Exhibición agregada correctamente." : "No se pudo agregar: temática vacía o repetida, o capacidad inválida para la sala.");
                    break;
                }
                case 2: {
                    ArrayList<Exhibicion> exhibiciones = sala.listarExhibiciones();
                    if (exhibiciones.isEmpty()) {
                        System.out.println("No hay exhibiciones registradas.");
                    }
                    for (Exhibicion exhibicion : exhibiciones){
                        mostrarExhibicion(exhibicion);
                    }
                    break;
                }
                case 3: {
                    System.out.print("Temática a buscar: ");
                    Exhibicion exhibicion = sala.buscarExhibicion(scanner.nextLine().trim());
                    if (exhibicion == null) {
                        System.out.println("Exhibición no encontrada.");
                    }
                    else {
                        mostrarExhibicion(exhibicion);
                    }
                    break;
                }
                case 4: {
                    System.out.print("Temática actual: ");
                    String original = scanner.nextLine().trim();
                    if (sala.buscarExhibicion(original) == null) {
                        System.out.println("Exhibición no encontrada.");
                        break;
                    }
                    System.out.print("Nueva temática: ");
                    String nueva = scanner.nextLine().trim();
                    System.out.print("Nueva capacidad máxima de obras: ");
                    int capacidad = leerEntero();
                    System.out.println(sala.editarExhibicion(original, nueva, capacidad) ? "Exhibición modificada correctamente.": "No se pudo editar: temática vacía o repetida, o capacidad incompatible con la sala o sus obras.");
                    break;
                }
                case 5: {
                    System.out.print("Temática a eliminar: ");
                    System.out.println(sala.eliminarExhibicion(scanner.nextLine().trim()) ? "Exhibición eliminada correctamente." : "No se pudo eliminar: no existe o todavía contiene obras. Traslade sus obras primero.");
                    break;
                }
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarExhibicion(Exhibicion exhibicion) {
        System.out.println("Temática: " + exhibicion.getTematica() + " | Capacidad: " + exhibicion.getCapacidadMaxima() + " | Obras: " + exhibicion.getObrasExhibidas().size());
    }
}
