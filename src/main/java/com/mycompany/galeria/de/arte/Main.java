package com.mycompany.galeria.de.arte;

import javax.swing.JOptionPane;

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

        String[] opciones = {"Consola", "Ventanas"};

        int opcion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo desea utilizar el sistema?",
                "Galería de Arte",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (opcion == 0) {

            // Modo consola
            ConsolaMenu menu = new ConsolaMenu(galeria);
            menu.mostrarMenu();

        } else if (opcion == 1) {

            // Modo ventanas
            VentanaMenu ventana = new VentanaMenu(galeria);
            ventana.setVisible(true);

        } else {

            return;
        }

        if (persistencia.guardarSalas(galeria)) {
            System.out.println("Salas guardadas correctamente.");
        } else {
            System.out.println("Error: no se pudieron guardar las salas.");
        }
    }
}