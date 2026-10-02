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
import java.util.ArrayList;

public class VentanaClientes extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;

    public VentanaClientes(Galeria galeria) {
        this.galeria = galeria;

        setTitle("Gestión de Clientes");
        setSize(400, 280);
        setResizable(false);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GESTIÓN DE CLIENTES", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Clientes", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton btnListar = crearBoton("Ver totalidad de clientes históricos");
        btnListar.addActionListener(e -> listarClientes());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panelBotones.add(btnListar);
        panelBotones.add(Box.createVerticalGlue());

        // Ensamblado
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(panelBotones, BorderLayout.CENTER);

        setContentPane(contenido);
    }

    private void listarClientes() {
        ArrayList<Venta> ventas = galeria.getRegistroVentas();
        if (ventas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aún no hay clientes registrados.");
            return;
        }
        ArrayList<String> rutsImpresos = new ArrayList<>();
        StringBuilder sb = new StringBuilder("--- CLIENTES ---\n");
        for (Venta v : ventas) {
            Cliente c = v.getCliente();
            if (!rutsImpresos.contains(c.getRut())) {
                sb.append("RUT: ").append(c.getRut()).append(" | Nombre: ").append(c.getNombre()).append("\n");
                rutsImpresos.add(c.getRut());
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    // Crea un botón con el mismo estilo y tamaño que el menú principal
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(COLOR_BOTON);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(340, 45));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return boton;
    }
}