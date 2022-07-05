package org.carranza.java.msvc.veterinaria.msvcgestionproducto.repository;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository  extends JpaRepository<ProductoEntity, Long> {

    @Query("select p from ProductoEntity p where p.estado= 1")
    List<ProductoEntity> findAllCustom();

}