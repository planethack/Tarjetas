package com.carlos.tarjetas.models;

import java.io.Serializable;

public class TarjetaModel implements Serializable {
    private String id;
    private String numero;
    private Integer mes;
    private Integer anio;
    private Double cupo;
    private Double saldo;
    private Double deuda;
    private String franquicia;

    public TarjetaModel() {
    }

    public TarjetaModel(String id, String numero, Integer mes, Integer anio, Double cupo, Double saldo, Double deuda, String franquicia) {
        this.setId(id);
        this.setNumero(numero);
        this.setMes(mes);
        this.setAnio(anio);
        this.setCupo(cupo);
        this.setSaldo(saldo);
        this.setDeuda(deuda);
        this.setFranquicia(franquicia);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getCupo() {
        return cupo;
    }

    public void setCupo(Double cupo) {
        this.cupo = cupo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getDeuda() {
        return deuda;
    }

    public void setDeuda(Double deuda) {
        this.deuda = deuda;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
