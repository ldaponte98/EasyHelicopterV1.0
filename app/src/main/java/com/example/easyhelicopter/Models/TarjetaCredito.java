package com.example.easyhelicopter.Models;

public class TarjetaCredito {

    private Long numeroTarjeta;
    private String cvv;

    public TarjetaCredito(Long numero){
        this.numeroTarjeta = numero;
    }

    public TarjetaCredito(Long numero, String cvvCode){
        this.numeroTarjeta = numero;
        this.cvv = cvvCode;
    }

    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

}
