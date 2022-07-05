package org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.common.CodeEnum;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

    private CodeEnum code;
    private Object message;
    private Object data;

}