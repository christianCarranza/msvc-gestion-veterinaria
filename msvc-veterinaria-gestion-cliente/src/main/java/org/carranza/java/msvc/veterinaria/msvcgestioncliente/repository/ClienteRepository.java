package org.carranza.java.msvc.veterinaria.msvcgestioncliente.repository;

import org.carranza.java.msvc.veterinaria.msvcgestioncliente.models.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("select p from ClienteEntity p where p.estado= 1")
    List<ClienteEntity> findAllCustom();

}
