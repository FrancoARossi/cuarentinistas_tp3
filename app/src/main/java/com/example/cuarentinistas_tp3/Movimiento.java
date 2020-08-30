package com.example.cuarentinistas_tp3;

public class Movimiento {
    private String importe;
    private String detalle;
    private Boolean saliente;

    public Movimiento(String importe, String detalle, Boolean saliente) {
        this.importe = importe;
        this.detalle = detalle;
        this.saliente = saliente;
    }

    public Boolean getSaliente() {
        return saliente;
    }

    public void setSaliente(Boolean saliente) {
        this.saliente = saliente;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
