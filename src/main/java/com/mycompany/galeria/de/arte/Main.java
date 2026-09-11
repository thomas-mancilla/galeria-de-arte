package com.mycompany.galeria.de.arte;

public class Main {

    public static void main(String[] args) {
        Galeria galeria = new Galeria();
        ConsolaMenu menu = new ConsolaMenu(galeria);

        menu.mostrarMenu();
    }
}
