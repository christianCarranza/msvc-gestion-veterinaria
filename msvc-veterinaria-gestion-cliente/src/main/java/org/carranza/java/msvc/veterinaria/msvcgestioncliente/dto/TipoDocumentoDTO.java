package org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TipoDocumentoDTO extends GenericDTO {

    private Long idTipoDocumento;
    private String descripcion;
}
