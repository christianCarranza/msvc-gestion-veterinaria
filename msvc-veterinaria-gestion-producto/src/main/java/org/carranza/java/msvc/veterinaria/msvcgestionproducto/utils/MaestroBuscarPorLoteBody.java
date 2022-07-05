package org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils;

import lombok.Data;

import java.util.UUID;

@Data
public class MaestroBuscarPorLoteBody {
    private UUID[] ids;
    private Integer[] prefijos;
}
