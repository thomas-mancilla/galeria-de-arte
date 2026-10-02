package com.mycompany.galeria.de.arte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrestamos extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;

    public VentanaPrestamos(Galeria galeria) {
        this.galeria = galeria;

        setTitle("Gestión de Préstamos");
        setSize(450, 410);
        setResizable(false);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GESTIÓN DE PRÉSTAMOS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Préstamos", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton btnPedir = crearBoton("Pedir préstamo de Obra");
        JButton btnDevolver = crearBoton("Devolver préstamo de Obra");
        JButton btnActivos = crearBoton("Ver préstamos activos");
        JButton btnDisponibles = crearBoton("Ver obras disponibles para préstamo");

        btnPedir.addActionListener(e -> pedirPrestamo());
        btnDevolver.addActionListener(e -> devolverPrestamo());
        btnActivos.addActionListener(e -> verActivos());
        btnDisponibles.addActionListener(e -> verDisponibles());

        JButton[] botones = {btnPedir, btnDevolver, btnActivos, btnDisponibles};

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        for (int i = 0; i < botones.length; i++) {
            if (i > 0) panelBotones.add(Box.createRigidArea(new Dimension(0, 12)));
            panelBotones.add(botones[i]);
        }

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

    private void pedirPrestamo() {
        ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
        if (disponibles.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay obras."); return; }

        Obra[] obrasArray = disponibles.toArray(new Obra[0]);
        Obra obraSel = (Obra) JOptionPane.showInputDialog(this, "Seleccione obra:", "Préstamo", JOptionPane.QUESTION_MESSAGE, null, obrasArray, obrasArray[0]);
        if (obraSel == null) return;

        String rut = JOptionPane.showInputDialog(this, "RUT:");
        if (rut == null) return;
        String nombre = JOptionPane.showInputDialog(this, "Nombre:");
        String telefono = JOptionPane.showInputDialog(this, "Teléfono:");
        String correo = JOptionPane.showInputDialog(this, "Correo:");
        String fechaDev = JOptionPane.showInputDialog(this, "Fecha devolución (Ej. 2026-12-01):");

        Cliente cliente = new Cliente(rut, nombre, telefono, correo);
        galeria.getBodega().eliminarObra(obraSel, EstadoObra.GUARDADA);
        obraSel.setEstado(EstadoObra.PRESTADA);
        galeria.getBodega().agregarObra(obraSel, EstadoObra.PRESTADA);

        Prestamo p = new Prestamo(obraSel, cliente, java.time.LocalDate.now().toString(), fechaDev);
        galeria.getRegistroPrestamos().add(p);
        JOptionPane.showMessageDialog(this, "Préstamo registrado.");
    }

    private void devolverPrestamo() {
        ArrayList<Prestamo> prestamos = galeria.getRegistroPrestamos();
        if (prestamos.isEmpty()) { JOptionPane.showMessageDialog(this, "Sin préstamos activos."); return; }

        String[] ops = new String[prestamos.size()];
        for (int i = 0; i < prestamos.size(); i++) ops[i] = "Obra: " + prestamos.get(i).getObra().getTitulo();

        String sel = (String) JOptionPane.showInputDialog(this, "Devolver:", "Devolver", JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
        if (sel == null) return;

        int idx = -1;
        for (int i = 0; i < ops.length; i++) if (ops[i].equals(sel)) idx = i;

        Obra obraDevuelta = prestamos.get(idx).getObra();
        galeria.getBodega().eliminarObra(obraDevuelta, EstadoObra.PRESTADA);
        obraDevuelta.setEstado(EstadoObra.GUARDADA);
        galeria.getBodega().agregarObra(obraDevuelta, EstadoObra.GUARDADA);

        galeria.getRegistroPrestamos().remove(prestamos.get(idx));
        JOptionPane.showMessageDialog(this, "Obra devuelta a bodega.");
    }

    private void verActivos() {
        ArrayList<Prestamo> prestamos = galeria.getRegistroPrestamos();
        if (prestamos.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay préstamos."); return; }
        StringBuilder sb = new StringBuilder();
        for (Prestamo p : prestamos) sb.append("Obra: ").append(p.getObra().getTitulo()).append(" | Devolución: ").append(p.getFechaDevolucion()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void verDisponibles() {
        ArrayList<Obra> disponibles = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
        if (disponibles.isEmpty()) { JOptionPane.showMessageDialog(this, "Bodega vacía."); return; }
        StringBuilder sb = new StringBuilder();
        for (Obra o : disponibles) sb.append("- ").append(o.getTitulo()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }
}