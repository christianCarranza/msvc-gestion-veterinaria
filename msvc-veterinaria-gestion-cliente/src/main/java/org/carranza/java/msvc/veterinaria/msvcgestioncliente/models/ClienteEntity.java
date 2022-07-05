package org.carranza.java.msvc.veterinaria.msvcgestioncliente.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "CLIENTES")
public class ClienteEntity extends GenericEntity implements Serializable {

    private static final long serialVersionUID = -2170897015344177815L;

    @Id
    @Column(name = "ID_CLIENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_DOC_FK", referencedColumnName="ID_TIPO_DOC")
    private TipoDocumentoEntity idTipoDoc;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APE_PATERNO", nullable = false, length = 90)
    private String apellidoPaterno;

    @Column(name = "APE_MATERNO", nullable = false, length = 90)
    private String apellidoMaterno;

    @Column(name = "EDAD")
    private Integer edad;

    @Column(name = "SEXO")
    private String sexo;

    @Column(name = "DOCUMENTO", unique=true, nullable = false)
    private String documento;

    @Column(name = "TELEFONO", length = 8)
    private String telefono;

    @Column(name = "CELULAR", nullable = false, length = 9)
    private String celular;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "DIREC_REFERENCIA")
    private String direcReferencia;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "RUTA_IMAGEN")
    private String rutaImagen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClienteEntity that = (ClienteEntity) o;
        return idCliente != null && Objects.equals(idCliente, that.idCliente);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
