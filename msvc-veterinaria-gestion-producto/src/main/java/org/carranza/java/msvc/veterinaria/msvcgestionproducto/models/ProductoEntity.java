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
@Table(name = "PRODUCTOS")
public class ProductoEntity extends GenericEntity implements Serializable {

    private static final long serialVersionUID = -2170897015344177815L;

    @Id
    @Column(name = "ID_PRODUCTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqProducto")
    @SequenceGenerator(sequenceName = "SEQ_PRODUCTO", allocationSize = 1, name = "seqProducto")
    private Long idProducto;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA_FK", referencedColumnName="ID_CATEGORIA")
    private CategoriaEntity categoria;

    @Column(name = "DESCRIPCION", nullable = false, length = 250)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "STOCK", nullable = false, length = 4)
    private Integer stock;


}
