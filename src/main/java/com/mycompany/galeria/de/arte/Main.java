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
            //return;
        }

        String[] opciones = {"Consola", "Ventanas"};

        int opcion = JOptionPane.showOptionDialog( null, "¿Cómo desea utilizar el sistema?", "Galería de Arte", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0] );

        if (opcion == 0) {
            ConsolaMenu menu = new ConsolaMenu(galeria);
            menu.mostrarMenu();
            if (persistencia.guardarSalas(galeria)) {
                System.out.println("Datos guardados correctamente.");
            }
            else {
                System.out.println("Error: no se pudieron guardar los datos.");
            }

        }
        else if (opcion == 1) {
            VentanaMenu ventana = new VentanaMenu(galeria, persistencia);
            ventana.setVisible(true);
        }
    }
}
