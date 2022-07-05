package org.carranza.java.msvc.veterinaria.msvcgestionproducto.controller;

import lombok.extern.slf4j.Slf4j;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.CodeEnum;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.controller.generic.GenericController;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.ProductoDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.response.CustomResponse;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.ProductoService;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils.ExportarUtil;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils.FechasUtil;
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

import static org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.MessageConstants.*;

@Slf4j
@RestController
@RequestMapping(API_PRODUCTO)
public class ProductoController extends GenericController {

    private static final String FORMATO = "dd/MM/yyyy";
    private final String api = "producto";
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<?> Search(
            @RequestParam(name = "usuariologin") Long usuarioLogin,
            @RequestParam(name = "producto", required = false) String producto,
            @RequestParam(name = "categoria", required = false) Long categoria,
            @RequestParam(name = "stock", required = false) Integer stock,
            @RequestParam(name = "precio", required = false) Double precio,
            @RequestParam(name = "fechainicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechainicio,
            @RequestParam(name = "fechafin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechafin,
            Pageable paginador
    ){
        try {
            //Validar fechas
            String validateString = FechasUtil.validateDateTimeSearch(fechainicio, fechafin);
            if(!validateString.isEmpty()) throw new RuntimeException(validateString);

            Page<ProductoDTO> productoDTOPage = this.productoService.search(producto, categoria, stock, precio, fechainicio, fechafin, paginador);

            return ResponseEntity.ok(
                    CustomResponse.builder().code(CodeEnum.SUCCESS).message("Informacion de "+api)
                            .data(productoDTOPage).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<?> Export(
            @RequestParam(name = "usuariologin") Long usuarioLogin,
            @RequestParam(name = "producto", required = false) String producto,
            @RequestParam(name = "categoria", required = false) Long categoria,
            @RequestParam(name = "stock", required = false) Integer stock,
            @RequestParam(name = "precio", required = false) Double precio,
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

            List<ProductoDTO> productoDTOList = this.productoService.export(producto, categoria, stock, precio, fechainicio, fechafin, ordencol, ordendir);

            List<String[]> rowList = new ArrayList<>();
            for (var productoL : productoDTOList) {
                var productoRow = new String[] {
                        String.valueOf(productoL.getIdProducto()),
                        FechasUtil.convertDateToString(productoL.getFechaCreacion(), FORMATO),
                        productoL.getNombre(),
                        String.valueOf(productoL.getCategoria().getIdCategoria()),
                        String.valueOf(productoL.getDescripcion()),
                        String.valueOf(productoL.getPrecio()),
                        String.valueOf(productoL.getStock()),
                        productoL.getEstado()==1?"Activo":"Inactivo"
                };
                rowList.add(productoRow);
            }
            String[] columnas = {"Id", "Fecha de Creacion","Nombre", "Categoria","Descripcion","Precio","Stock","Estado"};
            String nombreDoc = "Registro_producto_export_" + FechasUtil.convertDateToString(LocalDateTime.now(), "yyyy-MM-dd HH_mm_ss");

            if (extension.equalsIgnoreCase("xlsx")) {
                ExportarUtil.aExcel(rowList, nombreDoc + ".xlsx", "LISTADO DE PRODUCTOS", "Productos", columnas, response);
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
            var silabo = this.productoService.findById(id);
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
    public ResponseEntity<?> Save(@Valid @RequestBody ProductoDTO productoDTO){
        try{

            var resProducto = this.productoService.save(productoDTO);
            if (Objects.isNull(resProducto)) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resProducto, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PostMapping("/save-list")
    public ResponseEntity<?> guardarList(@Valid @RequestBody List<ProductoDTO> productoDTOList){
        try{

            var resProductoList1 = this.productoService.saveList(productoDTOList);
            if (resProductoList1.isEmpty()) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resProductoList1, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> Update(@PathVariable Long id, @RequestBody ProductoDTO productoDTO){
        try{
            var resProducto = this.productoService.update(id, productoDTO);
            if (Objects.isNull(resProducto)) {
                super.getBadRequest(MSG_ERROR_UPDATE_API + api);
            }
            return super.getCreated(resProducto, MSG_EXITO_UPDATE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            this.productoService.delete(id);
            return super.getCreated("", MSG_EXITO_DELETE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }


}
