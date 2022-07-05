package org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.mapper;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.ProductoDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.models.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoEntity postDtoToEntity(ProductoDTO dto);

    ProductoDTO entityToGetDto(ProductoEntity entity);

}
