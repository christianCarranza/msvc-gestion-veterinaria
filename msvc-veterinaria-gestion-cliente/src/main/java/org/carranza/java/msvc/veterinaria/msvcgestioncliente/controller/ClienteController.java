package org.carranza.java.msvc.veterinaria.msvcgestioncliente.controller;

import lombok.extern.slf4j.Slf4j;
import static org.carranza.java.msvc.veterinaria.msvcgestioncliente.common.MessageConstants.*;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.common.CodeEnum;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.controller.generic.GenericController;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.ClienteDTO;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.dto.response.CustomResponse;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.service.ClienteService;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.utils.ExportarUtil;
import org.carranza.java.msvc.veterinaria.msvcgestioncliente.utils.FechasUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(API_CLIENTE)
public class ClienteController extends GenericController {

    private static final String FORMATO = "dd/MM/yyyy";
    private final String api = "cliente";
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<?> Search(
            @RequestParam(name = "usuariologin") Long usuarioLogin,
            @RequestParam(name = "cliente", required = false) String cliente,
            @RequestParam(name = "edad", required = false) Integer edad,
            @RequestParam(name = "sexo", required = false) String sexo,
            @RequestParam(name = "tipoDocumento", required = false) Long tipoDocumento,
            @RequestParam(name = "documento", required = false) String documento,
            @RequestParam(name = "telefono", required = false) String telefono,
            @RequestParam(name = "celular", required = false) String celular,
            @RequestParam(name = "direccion", required = false) String direccion,
            @RequestParam(name = "correo", required = false) String correo,
            @RequestParam(name = "fechainicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechainicio,
            @RequestParam(name = "fechafin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechafin,
            Pageable paginador
    ){
        try {
            //Validar fechas
            String validateString = FechasUtil.validateDateTimeSearch(fechainicio, fechafin);
            if(!validateString.isEmpty()) throw new RuntimeException(validateString);

            Page<ClienteDTO> clienteDTOPage = this.clienteService.search(cliente, edad, sexo, tipoDocumento, documento, telefono, celular, direccion, correo, fechainicio, fechafin, paginador);

            return ResponseEntity.ok(
                    CustomResponse.builder().code(CodeEnum.SUCCESS).message("Informacion de "+api)
                            .data(clienteDTOPage).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<?> Export(
            @RequestParam(name = "usuariologin") Long usuarioLogin,
            @RequestParam(name = "cliente", required = false) String cliente,
            @RequestParam(name = "edad", required = false) Integer edad,
            @RequestParam(name = "sexo", required = false) String sexo,
            @RequestParam(name = "tipoDocumento", required = false) Long tipoDocumento,
            @RequestParam(name = "documento", required = false) String documento,
            @RequestParam(name = "telefono", required = false) String telefono,
            @RequestParam(name = "celular", required = false) String celular,
            @RequestParam(name = "direccion", required = false) String direccion,
            @RequestParam(name = "correo", required = false) String correo,
            @RequestParam(name = "fechainicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechainicio,
            @RequestParam(name = "fechafin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechafin,
            @RequestParam(value = "ordencol", required = false, defaultValue = "fechaRegistro") String ordencol,
            @RequestParam(value = "ordendir", required = false, defaultValue = "desc") String ordendir,
            @RequestParam(value = "extension", required = false, defaultValue = "xlsx") String extension,
            HttpServletResponse response
    ){
        try {
            String validateString = FechasUtil.validateDateTimeSearch(fechainicio, fechafin);
            if(!validateString.isEmpty()) throw new RuntimeException(validateString);

            List<ClienteDTO> clienteDTOList = this.clienteService.export(cliente, edad, sexo, tipoDocumento, documento, telefono, celular, direccion, correo, fechainicio, fechafin, ordencol, ordendir);

            List<String[]> rowList = new ArrayList<>();
            for (var clienteL : clienteDTOList) {
                var clienteRow = new String[] {
                        String.valueOf(clienteL.getIdCliente()),
                        FechasUtil.convertDateToString(clienteL.getFechaCreacion(), FORMATO),
                        clienteL.getNombreCompleto(),
                        String.valueOf(clienteL.getEdad()),
                        String.valueOf(clienteL.getSexo()),
                        String.valueOf(clienteL.getDocumento()),
                        String.valueOf(clienteL.getTelefono()),
                        String.valueOf(clienteL.getCelular()),
                        String.valueOf(clienteL.getDireccion()),
                        String.valueOf(clienteL.getDirecReferencia()),
                        String.valueOf(clienteL.getCorreo()),
                        clienteL.getEstado()==0?"Activo":"Inactivo"
                };
                rowList.add(clienteRow);
            }
            String[] columnas = {"Id", "Fecha de Creacion","Nombre", "Edad","Sexo","Documento","Telefono","Celular","Direccion","DirecReferencia", "Correo", "Estado"};
            String nombreDoc = "Registro_cliente_export_" + FechasUtil.convertDateToString(LocalDateTime.now(), "yyyy-MM-dd HH_mm_ss");

            if (extension.equalsIgnoreCase("xlsx")) {
                ExportarUtil.aExcel(rowList, nombreDoc + ".xlsx", "LISTADO DE CLIENTES", "Clientes", columnas, response);
            } else {
                try {
                    ExportarUtil.aCsv(rowList, nombreDoc + ".csv", columnas, response);
                } catch (IOException e) {
                    log.error("Error IOException", e);
                }
            }

            return ResponseEntity.ok(
                    CustomResponse.builder().code(CodeEnum.SUCCESS).message("Informacion encontrada")
                            .data("").build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> FindById(@PathVariable(name = "id") Long id) {
        try {
            var silabo = this.clienteService.findById(id);
            if (silabo.isPresent()) {
                return ResponseEntity.ok(
                        CustomResponse.builder().code(CodeEnum.SUCCESS).message("informacion del "+api+" buscado")
                                .data(silabo).build());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.builder().code(CodeEnum.WARNING)
                    .message("No existe el "+api+" buscado").build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> Save(@Valid @RequestBody ClienteDTO clienteDTO){
        try{

            var resCliente = this.clienteService.save(clienteDTO);
            if (Objects.isNull(resCliente)) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resCliente, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PostMapping("/save-list")
    public ResponseEntity<?> guardarList(@Valid @RequestBody List<ClienteDTO> clienteDTOList){
        try{

            var resClienteList1 = this.clienteService.saveList(clienteDTOList);
            if (resClienteList1.isEmpty()) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resClienteList1, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> Update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        try{
            var resCliente = this.clienteService.update(id, clienteDTO);
            if (Objects.isNull(resCliente)) {
                super.getBadRequest(MSG_ERROR_UPDATE_API + api);
            }
            return super.getCreated(resCliente, MSG_EXITO_UPDATE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            this.clienteService.delete(id);
            return super.getCreated("", MSG_EXITO_DELETE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }


}
