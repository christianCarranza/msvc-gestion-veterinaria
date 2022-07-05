package org.carranza.java.msvc.veterinaria.msvcgestionproducto.controller.generic;

import org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.CodeEnum;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.response.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.CodeEnum.*;
import static org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.MessageConstants.*;
public class GenericController {

    protected ResponseEntity<List<Map<String, String>>> getErrors(BindingResult result) {

        List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {

                    Map<String, String> error = new HashMap<>();

                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }
        ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    protected ResponseEntity<?> getNotContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    protected ResponseEntity<?> getError(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomResponse
                .builder()
                .code(ERROR)
                .message(MSG_ERROR_INTERNO_API+msg)
                .build());
    }

    protected ResponseEntity<?> getCreated(Object obj, String msg) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CustomResponse
                        .builder()
                        .code(SUCCESS)
                        .message(msg)
                        .data(obj)
                        .build()
        );
    }

    protected ResponseEntity<?> getNotFount(Optional opt) {
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    CustomResponse.builder().code(CodeEnum.WARNING).message("No existe registro").build());
        }

        return ResponseEntity.ok(CustomResponse.builder().code(CodeEnum.SUCCESS).message("Exito al recupera registro")
                .data(opt.get()).build());
    }

    protected ResponseEntity<?> getBadRequest(String msg) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                CustomResponse
                        .builder()
                        .code(WARNING)
                        .message(msg)
                        .build()
        );
    }
}
