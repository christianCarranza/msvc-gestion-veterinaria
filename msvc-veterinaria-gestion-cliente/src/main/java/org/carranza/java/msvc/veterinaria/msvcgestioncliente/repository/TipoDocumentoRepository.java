package org.carranza.java.msvc.veterinaria.msvcgestioncliente.repository;

import org.carranza.java.msvc.veterinaria.msvcgestioncliente.models.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository  extends JpaRepository<TipoDocumentoEntity, Long> {

    @Query("select p from TipoDocumentoEntity p where p.estado= 1")
    List<TipoDocumentoEntity> findAllCustom();

}
