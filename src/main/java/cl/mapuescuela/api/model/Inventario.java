package cl.mapuescuela.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    private Integer productoId;

    private Integer stockDisponible;

    public Inventario() {
    }

    public Inventario(
            Integer productoId,
            Integer stockDisponible) {
        this.productoId = productoId;
        this.stockDisponible = stockDisponible;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }
}