package org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.mapper;

import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.ClienteDTO;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.models.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteEntity postDtoToEntity(ClienteDTO dto);

    ClienteDTO entityToGetDto(ClienteEntity entity);

}
