package com.example.cuarentinistas_tp3;

public class Movimiento {
    private String importe;
    private String detalle;

    public Movimiento(String importe, String detalle) {
        this.importe = importe;
        this.detalle = detalle;
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
