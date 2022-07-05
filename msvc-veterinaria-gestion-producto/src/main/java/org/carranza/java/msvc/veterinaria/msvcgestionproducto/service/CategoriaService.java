package org.carranza.java.msvc.veterinaria.msvcgestionproducto.service;


import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.CategoriaDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoriaService  extends GenericeService<CategoriaDTO>{
    Page<CategoriaDTO> search(Pageable paginador) throws ServiceException;

    List<CategoriaDTO> export() throws ServiceException;

    List<CategoriaDTO> saveList(List<CategoriaDTO> categoriaDTOList) throws ServiceException;
}
