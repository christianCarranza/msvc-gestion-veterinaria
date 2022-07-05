package org.carranza.java.msvc.veterinaria.msvcgestionproducto.repository;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository  extends JpaRepository<CategoriaEntity, Long> {

    @Query("select p from CategoriaEntity p where p.estado= 1")
    List<CategoriaEntity> findAllCustom();

}
