package org.carranza.java.msvc.veterinaria.msvcgestioncliente.service;

import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.ClienteDTO;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ClienteService extends GenericeService<ClienteDTO>{

    Page<ClienteDTO> search(String cliente, Integer edad, String sexo, Long tipoDocumento, String documento, String telefono, String celular, String direccion, String correo, LocalDateTime fechainicio, LocalDateTime fechafin, Pageable paginador) throws ServiceException;

    List<ClienteDTO> export(String cliente, Integer edad, String sexo, Long tipoDocumento, String documento, String telefono, String celular, String direccion, String correo, LocalDateTime fechainicio, LocalDateTime fechafin, String ordencol, String ordendir) throws ServiceException;

    List<ClienteDTO> saveList(List<ClienteDTO> clienteDTOList) throws ServiceException;

}
