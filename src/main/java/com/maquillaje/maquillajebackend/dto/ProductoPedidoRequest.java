package com.maquillaje.maquillajebackend.dto;

public class ProductoPedidoRequest {

    private Long id;
    private Integer cantidad;

    public ProductoPedidoRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}