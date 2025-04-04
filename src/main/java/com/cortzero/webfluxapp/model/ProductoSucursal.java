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
    private long productId;
    private long sucursalId;
    private int stock;

    @Override
    public String toString() {
        return "ProductoSucursal{" +
                "id=" + id +
                ", productId=" + productId +
                ", sucursalId=" + sucursalId +
                ", stock=" + stock +
                '}';
    }
}
