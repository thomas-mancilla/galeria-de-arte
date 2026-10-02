package com.mycompany.galeria.de.arte;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaMenu extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;
    private Persistencia persistencia;

    public VentanaMenu(Galeria galeria, Persistencia persistencia) {
        this.galeria = galeria;
        this.persistencia = persistencia;

        setTitle("Galería de Arte");
        setSize(400, 450);
        setResizable(false);
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
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GALERÍA DE ARTE", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Menú principal", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton botonObras = crearBoton("Obras");
        JButton botonSalas = crearBoton("Salas");
        JButton botonClientes = crearBoton("Clientes");
        JButton botonVentas = crearBoton("Ventas");
        JButton botonPrestamos = crearBoton("Préstamos");

        botonObras.addActionListener(e -> {
            VentanaObras vObras = new VentanaObras(galeria);
            vObras.setVisible(true);
        });

        botonSalas.addActionListener(e -> {
            VentanaSalas vSalas = new VentanaSalas(galeria);
            vSalas.setVisible(true);
        });

        botonClientes.addActionListener(e -> {
            VentanaClientes vClientes = new VentanaClientes(galeria);
            vClientes.setVisible(true);
        });

        botonVentas.addActionListener(e -> {
            VentanaVentas vVentas = new VentanaVentas(galeria);
            vVentas.setVisible(true);
        });

        botonPrestamos.addActionListener(e -> {
            VentanaPrestamos vPrestamos = new VentanaPrestamos(galeria);
            vPrestamos.setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        panelBotones.add(botonObras);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(botonSalas);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(botonClientes);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(botonVentas);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(botonPrestamos);

        // Ensamblado
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(panelBotones, BorderLayout.CENTER);

        setContentPane(contenido);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 15));
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(300, 45));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return boton;
    }
}