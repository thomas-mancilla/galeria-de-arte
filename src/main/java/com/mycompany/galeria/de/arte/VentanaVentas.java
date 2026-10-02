package com.mycompany.galeria.de.arte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaVentas extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;

    public VentanaVentas(Galeria galeria) {
        this.galeria = galeria;

        setTitle("Gestión de Ventas");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GESTIÓN DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Ventas", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton btnVender = crearBoton("Realizar una venta");
        JButton btnHistorial = crearBoton("Mostrar ventas de la galería");

        btnVender.addActionListener(e -> realizarVenta());
        btnHistorial.addActionListener(e -> mostrarHistorial());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panelBotones.add(btnVender);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
        panelBotones.add(btnHistorial);
        panelBotones.add(Box.createVerticalGlue());

        // Ensamblado
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.add(encabezado, BorderLayout.NORTH);
        contenido.add(panelBotones, BorderLayout.CENTER);

        setContentPane(contenido);
    }

    // Crea un botón con el mismo estilo que el menú principal
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

    private void realizarVenta() {
        ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
        if (disponibles.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay obras disponibles."); return; }

        Obra[] obrasArray = disponibles.toArray(new Obra[0]);
        Obra obraSeleccionada = (Obra) JOptionPane.showInputDialog(this, "Seleccione obra:", "Venta",
                JOptionPane.QUESTION_MESSAGE, null, obrasArray, obrasArray[0]);

        if (obraSeleccionada == null) return;
        String rut = JOptionPane.showInputDialog(this, "RUT:");
        if (rut == null) return;
        String nombre = JOptionPane.showInputDialog(this, "Nombre:");
        String telefono = JOptionPane.showInputDialog(this, "Teléfono:");
        String correo = JOptionPane.showInputDialog(this, "Correo:");

        Cliente cliente = new Cliente(rut, nombre, telefono, correo);
        galeria.getBodega().eliminarObra(obraSeleccionada, EstadoObra.GUARDADA);
        obraSeleccionada.setEstado(EstadoObra.VENDIDA);
        galeria.getBodega().agregarObra(obraSeleccionada, EstadoObra.VENDIDA);

        Venta venta = new Venta(obraSeleccionada, cliente, java.time.LocalDate.now().toString(), obraSeleccionada.getPrecio());
        galeria.getRegistroVentas().add(venta);
        JOptionPane.showMessageDialog(this, "¡Venta exitosa!");
    }

    private void mostrarHistorial() {
        ArrayList<Venta> ventas = galeria.getRegistroVentas();
        if (ventas.isEmpty()) { JOptionPane.showMessageDialog(this, "Sin ventas."); return; }
        StringBuilder sb = new StringBuilder();
        for (Venta v : ventas) sb.append("Fecha: ").append(v.getFecha()).append(" | Cliente: ").append(v.getCliente().getNombre()).append(" | Monto: $").append(v.getPrecioVenta()).append("\n");
        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(sb.toString())));
    }
}