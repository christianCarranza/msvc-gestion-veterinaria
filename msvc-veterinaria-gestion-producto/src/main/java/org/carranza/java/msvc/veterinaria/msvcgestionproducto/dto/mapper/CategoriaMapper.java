package org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.mapper;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.CategoriaDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    CategoriaEntity postDtoToEntity(CategoriaDTO dto);

    CategoriaDTO entityToGetDto(CategoriaEntity entity);

}
