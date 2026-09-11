package com.mycompany.galeria.de.arte;

public class Prestamo {

    private Obra obra;
    private Cliente cliente;
    private String fechaInicio;
    private String fechaDevolucion;

    public Prestamo(Obra obra, Cliente cliente, String fechaInicio, String fechaDevolucion) {
        this.obra = obra;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters

    public Obra getObra() {
        return obra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    // Setters

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}