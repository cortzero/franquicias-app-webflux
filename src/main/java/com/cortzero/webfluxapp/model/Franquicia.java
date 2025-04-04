package com.cortzero.webfluxapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.Set;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Franquicia {

    @Id
    private long id;
    private String nombre;
    private Set<Sucursal> sucursales;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Franquicia that = (Franquicia) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Franquicia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sucursales=" + sucursales +
                '}';
    }

}
