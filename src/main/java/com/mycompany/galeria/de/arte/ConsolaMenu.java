package com.mycompany.galeria.de.arte;

import java.util.Scanner;

public class ConsolaMenu {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
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
            opcion = scanner.nextInt();

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
                        opcionObra = scanner.nextInt();

                        switch (opcionObra) {
                            case 1:
                                System.out.println("\nInsertar obra");
                                break;
                            case 2:
                                System.out.println("\nMostrar Obras");
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
        
        scanner.close();
    }
}