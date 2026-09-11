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

            switch (opcion) {

                case 1:

    int opcionObra;

    do {
        System.out.println("\nOBRAS");
        System.out.println("-----");
        System.out.println("[1] Insertar obra");
        System.out.println("[2] Mostrar obras");
        System.out.println("[3] Buscar obra");
        System.out.println("[4] Editar obra");
        System.out.println("[5] Eliminar obra");
        System.out.println("[0] Volver");

        System.out.print("\nSeleccione: ");
        opcionObra = leerEntero();

        switch (opcionObra) {

            case 1:
                System.out.println("\nInsertar obra");
                break;

            case 2:
                mostrarMenuSalas();
                break;

            case 3:
                System.out.println("\nBuscar obra");
                break;

            case 4:
                System.out.println("\nEditar obra");
                break;

            case 5:
                System.out.println("\nEliminar obra");
                break;

            case 0:
                System.out.println("\nVolviendo al menú principal...");
                break;

            default:
                System.out.println("\nOpción no válida.");
        }

    } while (opcionObra != 0);

    break;

                case 2:
                    System.out.println("\nGestionar salas");
                    break;

                case 3:
                    System.out.println("\nGestionar clientes");
                    break;

                case 4:
                    System.out.println("\nGestionar ventas");
                    break;

                case 5:
                    System.out.println("\nGestionar préstamos");
                    break;

                case 0:
                    System.out.println("\nSaliendo del sistema...");
                    break;

                default:
                    System.out.println("\nOpción no válida.");
            }

        } while (opcion != 0);
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
            System.out.print("Seleccione: ");

            opcion = leerEntero();
            switch (opcion) {
                case 1: {
                    System.out.print("ID de la sala: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine().trim();

                    System.out.print("Capacidad máxima de obras: ");
                    int capacidad = leerEntero();

                    Sala sala = new Sala(id, nombre, capacidad);

                    if (galeria.insertarSala(id, sala)) {
                        System.out.println("Sala agregada correctamente.");
                    }
                    else {
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
                    Sala sala = galeria.buscarSala(id);

                    if (sala == null) {
                        System.out.println("No existe una sala con ese ID.");
                    }
                    else {
                        mostrarSala(sala);
                    }
                    break;
                }

                case 4: {
                    System.out.print("Capacidad mínima: ");
                    int capacidad = leerEntero();

                    if (capacidad <= 0) {
                        System.out.println("La capacidad debe ser positiva.");
                    }
                    else {
                        mostrarSalas(galeria.buscarSala(capacidad));
                    }
                    break;
                }

                case 5: {
                    System.out.print("ID de la sala a editar: ");
                    String id = scanner.nextLine().trim();
                    if (galeria.buscarSala(id) == null) {
                        System.out.println("No existe una sala con ese ID.");
                        break;
                    }
                    System.out.print("Nuevo nombre: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Nueva capacidad: ");
                    int capacidad = leerEntero();

                    if (galeria.editarSala(id, nombre, capacidad)) {
                        System.out.println("Sala modificada correctamente.");
                    }
                    else {
                        System.out.println("Datos inválidos o capacidad insuficiente "+ "para las exhibiciones.");
                    }
                    break;
                }

                case 6: {
                    System.out.print("ID de la sala a eliminar: ");
                    String id = scanner.nextLine().trim();
                    if (galeria.eliminarSala(id)) {
                        System.out.println("Sala eliminada correctamente.");
                    }
                    else {
                        System.out.println("La sala no existe o todavía tiene exhibiciones.");
                    }
                    break;
                }

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        }
        while (opcion != 0);
    }

    private void mostrarSala(Sala sala) {
        System.out.println("ID: " + sala.getNumero()+ " | Nombre: " + sala.getNombre()+ " | Capacidad: " + sala.getCapacidadMaxObras());
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
