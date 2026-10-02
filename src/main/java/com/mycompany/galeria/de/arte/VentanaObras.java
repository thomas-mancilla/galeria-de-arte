package com.mycompany.galeria.de.arte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaObras extends JFrame {

    private static final Color COLOR_ENCABEZADO = new Color(44, 62, 80);
    private static final Color COLOR_FONDO = new Color(245, 245, 245);
    private static final Color COLOR_BOTON = new Color(52, 73, 94);

    private Galeria galeria;

    public VentanaObras(Galeria galeria) {
        this.galeria = galeria;

        setTitle("Gestión de Obras");
        setSize(450, 620);
        setResizable(false);
        setLocationRelativeTo(null);

        crearVentana();
    }

    private void crearVentana() {
        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(COLOR_ENCABEZADO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));

        JLabel titulo = new JLabel("GESTIÓN DE OBRAS", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Obras", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(189, 195, 199));

        encabezado.add(titulo, BorderLayout.CENTER);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        // Botones
        JButton btnIngresar = crearBoton("Ingresar nueva obra");
        JButton btnBodega = crearBoton("Ver inventario en Bodega");
        JButton btnExhibicion = crearBoton("Ver obras en Exhibición");
        JButton btnTrasladar = crearBoton("Trasladar obra");
        JButton btnEditar = crearBoton("Editar datos de la obra");
        JButton btnEliminar = crearBoton("Dar de baja / Eliminar obra");
        JButton btnBuscarEstado = crearBoton("Buscar obra por estado");
        JButton btnBuscarTitulo = crearBoton("Buscar obra por título");
        JButton btnSeguro = crearBoton("Calcular seguro total");

        btnIngresar.addActionListener(e -> ingresarObra());
        btnBodega.addActionListener(e -> verBodega());
        btnExhibicion.addActionListener(e -> verExhibicion());
        btnTrasladar.addActionListener(e -> trasladarObra());
        btnEditar.addActionListener(e -> editarObra());
        btnEliminar.addActionListener(e -> eliminarObra());
        btnBuscarEstado.addActionListener(e -> buscarPorEstado());
        btnBuscarTitulo.addActionListener(e -> buscarPorTitulo());
        btnSeguro.addActionListener(e -> calcularSeguro());

        JButton[] botones = {btnIngresar, btnBodega, btnExhibicion, btnTrasladar, btnEditar,
                             btnEliminar, btnBuscarEstado, btnBuscarTitulo, btnSeguro};

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        for (int i = 0; i < botones.length; i++) {
            if (i > 0) panelBotones.add(Box.createRigidArea(new Dimension(0, 8)));
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
        boton.setPreferredSize(new Dimension(340, 40));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return boton;
    }

    private void ingresarObra() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título de la obra:");
            if (titulo == null || titulo.trim().isEmpty()) return;

            int precio = Integer.parseInt(JOptionPane.showInputDialog(this, "Precio:"));
            int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año de creación:"));
            String artista = JOptionPane.showInputDialog(this, "Nombre del artista:");
            if (artista == null || artista.trim().isEmpty()) return;

            String[] opciones = {"Óleo", "Escultura", "Fotografía"};
            int tipo = JOptionPane.showOptionDialog(this, "Seleccione el tipo de obra:", "Tipo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            Artista autor = new Artista(artista, "Desconocida");
            Obra nuevaObra;

            if (tipo == 0) nuevaObra = new Oleo(autor, titulo, EstadoObra.GUARDADA, precio, anio);
            else if (tipo == 1) nuevaObra = new Escultura(autor, titulo, EstadoObra.GUARDADA, precio, anio);
            else nuevaObra = new Fotografia(autor, titulo, EstadoObra.GUARDADA, precio, anio);

            galeria.getBodega().agregarObra(nuevaObra, EstadoObra.GUARDADA);
            JOptionPane.showMessageDialog(this, "Obra ingresada exitosamente a la Bodega.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verBodega() {
        ArrayList<Obra> obras = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
        if (obras.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La bodega está vacía.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- INVENTARIO EN BODEGA ---\n");
        for (Obra o : obras) sb.append("- ").append(o.getTitulo()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void verExhibicion() {
        StringBuilder sb = new StringBuilder("--- OBRAS EN EXHIBICIÓN ---\n");
        boolean hayObras = false;
        for (Sala sala : galeria.listarSalas()) {
            for (Exhibicion exh : sala.getExhibiciones()) {
                if (!exh.getObrasExhibidas().isEmpty()) {
                    hayObras = true;
                    sb.append("\nSala: ").append(sala.getNombre()).append(" | Temática: ").append(exh.getTematica()).append("\n");
                    for (Obra o : exh.getObrasExhibidas()) sb.append("  -> ").append(o.getTitulo()).append("\n");
                }
            }
        }
        if (!hayObras) sb.append("No hay obras en exhibición.");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void trasladarObra() {
        String[] opciones = {"Bodega -> Exhibición", "Exhibición -> Bodega"};
        int seleccion = JOptionPane.showOptionDialog(this, "Seleccione el tipo de traslado:", "Trasladar Obra",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) {
            String titulo = JOptionPane.showInputDialog(this, "Título de la obra en BODEGA:");
            if (titulo == null) return;
            ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
            Obra obraATrasladar = null;
            for (Obra o : guardadas) {
                if (o.getTitulo().equalsIgnoreCase(titulo)) { obraATrasladar = o; break; }
            }
            if (obraATrasladar == null) { JOptionPane.showMessageDialog(this, "Obra no encontrada."); return; }

            String idSala = JOptionPane.showInputDialog(this, "ID de la Sala destino:");
            Sala sala = galeria.buscarSala(idSala);
            if (sala == null) { JOptionPane.showMessageDialog(this, "Sala no encontrada."); return; }

            String tematica = JOptionPane.showInputDialog(this, "Temática de la Exhibición:");
            Exhibicion exhDestino = sala.buscarExhibicion(tematica);
            if (exhDestino == null) { JOptionPane.showMessageDialog(this, "Exhibición no encontrada."); return; }

            galeria.getBodega().eliminarObra(obraATrasladar, EstadoObra.GUARDADA);
            obraATrasladar.setEstado(EstadoObra.EN_EXHIBICION);
            if (exhDestino.agregarObra(obraATrasladar)) {
                JOptionPane.showMessageDialog(this, "¡Traslado exitoso!");
            } else {
                galeria.getBodega().agregarObra(obraATrasladar, EstadoObra.GUARDADA);
                JOptionPane.showMessageDialog(this, "Error: Capacidad excedida o datos inválidos.");
            }
        } else if (seleccion == 1) {
            String titulo = JOptionPane.showInputDialog(this, "Título de la obra en EXHIBICIÓN:");
            if (titulo == null) return;
            boolean trasladada = false;
            for (Sala sala : galeria.listarSalas()) {
                for (Exhibicion exh : sala.getExhibiciones()) {
                    ArrayList<Obra> obras = exh.getObrasExhibidas();
                    for (int i = 0; i < obras.size(); i++) {
                        if (obras.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                            Obra o = obras.remove(i);
                            o.setEstado(EstadoObra.GUARDADA);
                            galeria.getBodega().agregarObra(o, EstadoObra.GUARDADA);
                            trasladada = true; break;
                        }
                    }
                    if (trasladada) break;
                }
                if (trasladada) break;
            }
            JOptionPane.showMessageDialog(this, trasladada ? "Obra devuelta a bodega." : "Obra no encontrada.");
        }
    }

    private void editarObra() {
        String titulo = JOptionPane.showInputDialog(this, "Título de la obra a editar:");
        if (titulo == null) return;
        Obra obraAEditar = null;
        try {
            ArrayList<Obra> encontradas = galeria.buscarObraGlobal(titulo);
            obraAEditar = encontradas.get(0);
        } catch (ObraNoEncontradaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage()); return;
        }

        String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo título:", obraAEditar.getTitulo());
        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) obraAEditar.setTitulo(nuevoTitulo);
        try {
            String precio = JOptionPane.showInputDialog(this, "Nuevo precio (-1 para no cambiar):", obraAEditar.getPrecio());
            if (precio != null && Integer.parseInt(precio) != -1) obraAEditar.setPrecio(Integer.parseInt(precio));
            String anio = JOptionPane.showInputDialog(this, "Nuevo año (-1 para no cambiar):", obraAEditar.getAnioCreacion());
            if (anio != null && Integer.parseInt(anio) != -1) obraAEditar.setAnioCreacion(Integer.parseInt(anio));
            JOptionPane.showMessageDialog(this, "Datos actualizados.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato numérico inválido.");
        }
    }

    private void eliminarObra() {
        String titulo = JOptionPane.showInputDialog(this, "Título de la obra a eliminar:");
        if (titulo == null) return;
        boolean eliminada = false;
        ArrayList<Obra> guardadas = galeria.getBodega().obtenerObrasPorEstado(EstadoObra.GUARDADA);
        for (Obra o : guardadas) {
            if (o.getTitulo().equalsIgnoreCase(titulo)) {
                galeria.getBodega().eliminarObra(o, EstadoObra.GUARDADA);
                eliminada = true; break;
            }
        }
        if (!eliminada) {
            for (Sala sala : galeria.listarSalas()) {
                for (Exhibicion exh : sala.getExhibiciones()) {
                    ArrayList<Obra> obras = exh.getObrasExhibidas();
                    for (int i = 0; i < obras.size(); i++) {
                        if (obras.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                            obras.remove(i);
                            eliminada = true; break;
                        }
                    }
                    if (eliminada) break;
                }
                if (eliminada) break;
            }
        }
        JOptionPane.showMessageDialog(this, eliminada ? "Obra eliminada." : "Obra no encontrada.");
    }

    private void buscarPorEstado() {
        String[] opciones = {"GUARDADA", "EN_EXHIBICION", "PRESTADA", "VENDIDA"};
        String estadoSel = (String) JOptionPane.showInputDialog(this, "Seleccione el estado:", "Buscar",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (estadoSel != null) {
            ArrayList<Obra> obras = galeria.getBodega().buscarObras(EstadoObra.valueOf(estadoSel));
            if (obras.isEmpty()) JOptionPane.showMessageDialog(this, "No hay obras en este estado.");
            else {
                StringBuilder sb = new StringBuilder("Obras encontradas:\n");
                for (Obra o : obras) sb.append("- ").append(o.getTitulo()).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        }
    }

    private void buscarPorTitulo() {
        String titulo = JOptionPane.showInputDialog(this, "Título exacto:");
        if (titulo == null) return;
        try {
            ArrayList<Obra> resultados = galeria.buscarObraGlobal(titulo);
            StringBuilder sb = new StringBuilder("Obras encontradas:\n");
            for (Obra o : resultados) sb.append("- ").append(o.getTitulo()).append("\n");
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (ObraNoEncontradaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void calcularSeguro() {
        double seguroBodega = 0.0;
        double seguroExhibicion = 0.0;
        for (EstadoObra estado : EstadoObra.values()) {
            for (Obra o : galeria.getBodega().obtenerObrasPorEstado(estado)) seguroBodega += o.calcularCostoSeguro();
        }
        for (Sala sala : galeria.listarSalas()) {
            for (Exhibicion exh : sala.getExhibiciones()) {
                for (Obra o : exh.getObrasExhibidas()) seguroExhibicion += o.calcularCostoSeguro();
            }
        }
        JOptionPane.showMessageDialog(this, "Seguro Bodega: $" + seguroBodega + "\nSeguro Exhibición: $" +
            seguroExhibicion + "\nCOSTO TOTAL: $" + (seguroBodega + seguroExhibicion));
    }
}