package com.mycompany.galeria.de.arte;

public class Venta {

    private Obra obra;
    private Cliente cliente;
    private String fecha;
    private int precioVenta;

    public Venta(Obra obra, Cliente cliente, String fecha, int precioVenta) {
        this.obra = obra;
        this.cliente = cliente;
        this.fecha = fecha;
        this.precioVenta = precioVenta;
    }

    // Getters

    public Obra getObra() {
        return obra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public int getPrecioVenta() {
        return precioVenta;
    }

    // Setters

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrecioVenta(int precioVenta) {
        this.precioVenta = precioVenta;
    }
}