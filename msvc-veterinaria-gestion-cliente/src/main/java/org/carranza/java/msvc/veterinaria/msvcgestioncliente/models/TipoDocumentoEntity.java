package org.carranza.java.msvc.veterinaria.msvcgestioncliente.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TIPO_DOCUMENTO")
public class TipoDocumentoEntity extends GenericEntity implements Serializable {

    private static final long serialVersionUID = -2170897015344177815L;

    @Id
    @Column(name = "ID_TIPO_DOC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoDocumento;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;


}
