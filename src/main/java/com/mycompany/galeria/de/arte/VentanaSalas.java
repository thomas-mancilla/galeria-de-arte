package com.mycompany.galeria.de.arte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaSalas extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;

    public VentanaSalas(Galeria galeria) {
        this.galeria = galeria;

        setTitle("Gestión de Salas");
        setSize(450, 540);
        setResizable(false);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GESTIÓN DE SALAS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Salas", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton btnInsertar = crearBoton("Insertar sala");
        JButton btnListar = crearBoton("Listar salas");
        JButton btnBuscarId = crearBoton("Buscar por ID");
        JButton btnBuscarCap = crearBoton("Buscar por capacidad mínima");
        JButton btnEditar = crearBoton("Editar sala");
        JButton btnEliminar = crearBoton("Eliminar sala");
        JButton btnGestionarExh = crearBoton("Gestionar exhibiciones");

        btnInsertar.addActionListener(e -> insertarSala());
        btnListar.addActionListener(e -> listarSalas());
        btnBuscarId.addActionListener(e -> buscarPorId());
        btnBuscarCap.addActionListener(e -> buscarPorCapacidad());
        btnEditar.addActionListener(e -> editarSala());
        btnEliminar.addActionListener(e -> eliminarSala());
        btnGestionarExh.addActionListener(e -> gestionarExhibiciones());

        JButton[] botones = {btnInsertar, btnListar, btnBuscarId, btnBuscarCap,
                             btnEditar, btnEliminar, btnGestionarExh};

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        for (int i = 0; i < botones.length; i++) {
            if (i > 0) panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
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
        boton.setPreferredSize(new Dimension(340, 42));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return boton;
    }

    private void insertarSala() {
        try {
            String id = JOptionPane.showInputDialog(this, "ID de la sala:");
            if (id == null) return;
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre == null) return;
            int cap = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad:"));
            Sala sala = new Sala(id, nombre, cap);
            JOptionPane.showMessageDialog(this, galeria.insertarSala(id, sala) ? "Sala agregada." : "Error: ID repetido o datos inválidos.");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Capacidad inválida."); }
    }

    private void listarSalas() {
        ArrayList<Sala> salas = galeria.listarSalas();
        if (salas.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay salas."); return; }
        StringBuilder sb = new StringBuilder("--- SALAS ---\n");
        for (Sala s : salas) sb.append("ID: ").append(s.getNumero()).append(" | Nombre: ").append(s.getNombre()).append(" | Cap: ").append(s.getCapacidadMaxObras()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void buscarPorId() {
        String id = JOptionPane.showInputDialog(this, "ID:");
        if (id == null) return;
        Sala s = galeria.buscarSala(id);
        JOptionPane.showMessageDialog(this, s != null ? "ID: " + s.getNumero() + " | Nombre: " + s.getNombre() : "No encontrada.");
    }

    private void buscarPorCapacidad() {
        try {
            String input = JOptionPane.showInputDialog(this, "Capacidad mínima:");
            if (input == null) return;
            int cap = Integer.parseInt(input);
            ArrayList<Sala> salas = galeria.buscarSala(cap);
            if (salas.isEmpty()) JOptionPane.showMessageDialog(this, "Ninguna cumple la condición.");
            else {
                StringBuilder sb = new StringBuilder("Salas encontradas:\n");
                for (Sala s : salas) sb.append("- ").append(s.getNombre()).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Número inválido."); }
    }

    private void editarSala() {
        try {
            String id = JOptionPane.showInputDialog(this, "ID a editar:");
            if (id == null || galeria.buscarSala(id) == null) { JOptionPane.showMessageDialog(this, "No existe."); return; }
            String nombre = JOptionPane.showInputDialog(this, "Nuevo nombre:");
            int cap = Integer.parseInt(JOptionPane.showInputDialog(this, "Nueva capacidad:"));
            JOptionPane.showMessageDialog(this, galeria.editarSala(id, nombre, cap) ? "Modificada." : "Error al editar.");
        } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Número inválido."); }
    }

    private void eliminarSala() {
        String id = JOptionPane.showInputDialog(this, "ID a eliminar:");
        if (id != null) JOptionPane.showMessageDialog(this, galeria.eliminarSala(id) ? "Eliminada." : "No se puede eliminar (no existe o tiene exhibiciones).");
    }

    private void gestionarExhibiciones() {
        String id = JOptionPane.showInputDialog(this, "ID de la sala:");
        if (id == null) return;
        Sala sala = galeria.buscarSala(id);
        if (sala == null) { JOptionPane.showMessageDialog(this, "Sala no encontrada."); return; }

        String[] opciones = {"Agregar", "Listar", "Eliminar"};
        int eleccion = JOptionPane.showOptionDialog(this, "Gestión de Exhibiciones", "Menú",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (eleccion == 0) {
            String tematica = JOptionPane.showInputDialog(this, "Temática:");
            int cap = Integer.parseInt(JOptionPane.showInputDialog(this, "Capacidad máxima:"));
            JOptionPane.showMessageDialog(this, sala.agregarExhibicion(new Exhibicion(tematica, cap)) ? "Agregada." : "Error.");
        } else if (eleccion == 1) {
            ArrayList<Exhibicion> exhs = sala.listarExhibiciones();
            if (exhs.isEmpty()) JOptionPane.showMessageDialog(this, "Vacía.");
            else {
                StringBuilder sb = new StringBuilder();
                for (Exhibicion e : exhs) sb.append("- Temática: ").append(e.getTematica()).append(" | Obras: ").append(e.getObrasExhibidas().size()).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } else if (eleccion == 2) {
            String tematica = JOptionPane.showInputDialog(this, "Temática a eliminar:");
            JOptionPane.showMessageDialog(this, sala.eliminarExhibicion(tematica) ? "Eliminada." : "Error.");
        }
    }
}