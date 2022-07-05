package org.carranza.java.msvc.veterinaria.msvcgestionproducto.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "CATEGORIAS")
public class CategoriaEntity extends GenericEntity implements Serializable {

    private static final long serialVersionUID = -2170897015344177815L;

    @Id
    @Column(name = "ID_CATEGORIA")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;


}
