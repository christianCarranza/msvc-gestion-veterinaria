package org.carranza.java.msvc.veterinaria.msvcgestionproducto.controller;

import lombok.extern.slf4j.Slf4j;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.common.CodeEnum;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.controller.generic.GenericController;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.CategoriaDTO;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.dto.response.CustomResponse;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.service.CategoriaService;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils.ExportarUtil;
import org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils.FechasUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(API_CATEGORIA)
public class CategoriaController extends GenericController {

    private static final String FORMATO = "dd/MM/yyyy";
    private final String api = "categoria";
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<?> Search(Pageable paginador){
        try {
            Page<CategoriaDTO> categoriaDTOPage = this.categoriaService.search(paginador);

            return ResponseEntity.ok(
                    CustomResponse.builder().code(CodeEnum.SUCCESS).message("Informacion de "+api)
                            .data(categoriaDTOPage).build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/export")
    @ResponseBody
    public ResponseEntity<?> Export(@RequestParam(value = "extension", required = false, defaultValue = "xlsx") String extension, HttpServletResponse response){
        try {
            List<CategoriaDTO> categoriaDTOList = this.categoriaService.export();

            List<String[]> rowList = new ArrayList<>();
            for (var categoriaL : categoriaDTOList) {
                var categoriaRow = new String[] {
                        String.valueOf(categoriaL.getIdCategoria()),
                        FechasUtil.convertDateToString(categoriaL.getFechaCreacion(), FORMATO),
                        String.valueOf(categoriaL.getDescripcion()),
                        categoriaL.getEstado()==1?"Activo":"Inactivo"
                };
                rowList.add(categoriaRow);
            }
            String[] columnas = {"Id", "Fecha de Creacion","Descripcion","Estado"};
            String nombreDoc = "Registro_categoria_export_" + FechasUtil.convertDateToString(LocalDateTime.now(), "yyyy-MM-dd HH_mm_ss");

            if (extension.equalsIgnoreCase("xlsx")) {
                ExportarUtil.aExcel(rowList, nombreDoc + ".xlsx", "LISTADO DE CATEORIAS DE PRODUCTOS", "Categorias", columnas, response);
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
            var silabo = this.categoriaService.findById(id);
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
    public ResponseEntity<?> Save(@Valid @RequestBody CategoriaDTO categoriaDTO){
        try{

            var resCategoria = this.categoriaService.save(categoriaDTO);
            if (Objects.isNull(resCategoria)) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resCategoria, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PostMapping("/save-list")
    public ResponseEntity<?> guardarList(@Valid @RequestBody List<CategoriaDTO> categoriaDTOList){
        try{

            var resCategoriaList1 = this.categoriaService.saveList(categoriaDTOList);
            if (resCategoriaList1.isEmpty()) {
                super.getBadRequest(MSG_ERROR_CREATE_API+api);
            }
            return super.getCreated(resCategoriaList1, MSG_EXITO_REGISTRO+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> Update(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){
        try{
            var resCategoria = this.categoriaService.update(id, categoriaDTO);
            if (Objects.isNull(resCategoria)) {
                super.getBadRequest(MSG_ERROR_UPDATE_API + api);
            }
            return super.getCreated(resCategoria, MSG_EXITO_UPDATE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            this.categoriaService.delete(id);
            return super.getCreated("", MSG_EXITO_DELETE+api);
        }catch (Exception e) {
            return super.getError(api);
        }
    }


}
