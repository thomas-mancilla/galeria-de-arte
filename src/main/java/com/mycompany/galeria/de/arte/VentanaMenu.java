package com.mycompany.galeria.de.arte;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaMenu extends JFrame {

    private Galeria galeria;

    public VentanaMenu(Galeria galeria) {

        this.galeria = galeria;

        setTitle("Galería de Arte");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {

        JPanel panel = new JPanel();

        JLabel titulo = new JLabel("GALERÍA DE ARTE");

        JButton botonObras = new JButton("Obras");
        JButton botonSalas = new JButton("Salas");
        JButton botonClientes = new JButton("Clientes");
        JButton botonVentas = new JButton("Ventas");
        JButton botonPrestamos = new JButton("Préstamos");

        panel.add(titulo);
        panel.add(botonObras);
        panel.add(botonSalas);
        panel.add(botonClientes);
        panel.add(botonVentas);
        panel.add(botonPrestamos);

        setContentPane(panel);
    }
}