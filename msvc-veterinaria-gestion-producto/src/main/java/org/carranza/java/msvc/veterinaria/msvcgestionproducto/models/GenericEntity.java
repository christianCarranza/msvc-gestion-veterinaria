package org.carranza.java.msvc.veterinaria.msvcgestionproducto.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public class GenericEntity {

    @Column(name = "ESTADO", nullable = false)
    private Integer estado = 0;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "USUARIO_CREACION", nullable = false)
    private Long usuarioCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    @Column(name = "USUARIO_MODIFICACION")
    private Long usuarioModificacion;

}
