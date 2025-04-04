package com.cortzero.webfluxapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto_sucursal")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductoSucursal {

    @Id
    private long id;
    private long productoId;
    private long sucursalId;
    private int stock;

    @Override
    public String toString() {
        return "ProductoSucursal{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", sucursalId=" + sucursalId +
                ", stock=" + stock +
                '}';
    }
}
