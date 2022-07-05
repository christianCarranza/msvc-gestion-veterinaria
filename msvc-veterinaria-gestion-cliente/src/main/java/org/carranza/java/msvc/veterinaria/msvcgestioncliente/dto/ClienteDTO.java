package org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto;

import lombok.*;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDTO extends GenericDTO{

    private Long idCliente;
    private TipoDocumentoDTO idTipoDoc;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer edad;
    private String sexo;
    private String documento;
    private String telefono;
    private String celular;
    private String direccion;
    private String direcReferencia;
    private String correo;
    private String rutaImagen;

    public String getNombreCompleto() {
        var nom = Optional.ofNullable(this.nombre).orElse("");
        var apePaterno = Optional.ofNullable(this.apellidoPaterno).orElse("");
        var apeMaterno = Optional.ofNullable(this.apellidoMaterno).orElse("");

        return String.join(" ", nom, apePaterno, apeMaterno).trim();
    }

}
