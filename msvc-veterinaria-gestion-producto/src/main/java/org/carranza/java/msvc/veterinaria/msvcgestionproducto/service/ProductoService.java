package org.carranza.java.msvc.veterinaria.msvcgestionproducto.service;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.ProductoDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductoService extends GenericeService<ProductoDTO>{

    Page<ProductoDTO> search(String producto, Long categoria, Integer stock, Double precio, LocalDateTime fechainicio, LocalDateTime fechafin, Pageable paginador) throws ServiceException;

    List<ProductoDTO> export(String producto, Long categoria, Integer stock, Double precio, LocalDateTime fechainicio, LocalDateTime fechafin, String ordencol, String ordendir) throws ServiceException;

    List<ProductoDTO> saveList(List<ProductoDTO> clienteDTOList) throws ServiceException;

}
