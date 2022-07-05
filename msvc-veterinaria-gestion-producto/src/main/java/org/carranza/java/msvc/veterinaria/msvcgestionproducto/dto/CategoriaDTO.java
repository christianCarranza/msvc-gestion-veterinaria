package org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoriaDTO extends GenericDTO {

    private Long idCategoria;
    private String descripcion;
}
