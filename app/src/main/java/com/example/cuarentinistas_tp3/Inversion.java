package com.example.cuarentinistas_tp3;

public class Inversion {
    private String nombreBono;
    private String detalle;

    public Inversion(String nombreBono, String detalle) {
        this.nombreBono = nombreBono;
        this.detalle = detalle;
    }

    public String getNombreBono() {
        return nombreBono;
    }

    public void setNombreBono(String nombreBono) {
        this.nombreBono = nombreBono;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
