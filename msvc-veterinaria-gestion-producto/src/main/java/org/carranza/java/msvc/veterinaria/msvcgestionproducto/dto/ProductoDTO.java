package org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto;

import lombok.*;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.CategoriaDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductoDTO  extends GenericDTO implements Serializable {
    private Long idProducto;
    private String nombre;
    private CategoriaDTO categoria;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
