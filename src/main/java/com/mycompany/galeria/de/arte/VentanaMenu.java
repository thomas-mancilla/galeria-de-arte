package com.mycompany.galeria.de.arte;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaMenu extends JFrame {

    private Galeria galeria;
    private Persistencia persistencia;

    public VentanaMenu(Galeria galeria, Persistencia persistencia) {
        this.galeria = galeria;
        this.persistencia = persistencia;

        setTitle("Galería de Arte");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        crearVentana();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                guardarYCerrar();
            }
        });
    }

    private void guardarYCerrar() {
        try {
            if (persistencia.guardarSalas(galeria)) {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog( this, "No se pudieron guardar los datos. La ventana seguira abierta.", "Error de guardado", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch (RuntimeException e) {
            JOptionPane.showMessageDialog( this, "Ocurrio un error al preparar los datos. La ventana seguira abierta.", "Error de guardado", JOptionPane.ERROR_MESSAGE );
        }
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
