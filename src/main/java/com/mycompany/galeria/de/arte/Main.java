package com.mycompany.galeria.de.arte;

public class Main {

    public static void main(String[] args) {
        Galeria galeria = new Galeria();
        Persistencia persistencia = new Persistencia();

        if (!persistencia.cargarSalas(galeria)) {
            System.out.println(
                "No se pudieron cargar las salas. "
                + "Revise salas.txt antes de continuar."
            );
            return;
        }

        ConsolaMenu menu = new ConsolaMenu(galeria);
        menu.mostrarMenu();

        if (persistencia.guardarSalas(galeria)) {
            System.out.println("Salas guardadas correctamente.");
        } else {
            System.out.println("Error: no se pudieron guardar las salas.");
        }
    }
}
