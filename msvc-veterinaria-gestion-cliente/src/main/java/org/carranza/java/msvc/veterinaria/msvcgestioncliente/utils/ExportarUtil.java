package org.carranza.java.msvc.veterinaria.msvcgestioncliente.utils;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class ExportarUtil {

  private ExportarUtil(){

  }

  public static void aCsv(List<String[]> lista, String nombreArchivo, String[] columnas, HttpServletResponse response) throws IOException {
    var writer = response.getWriter();
    response.setContentType("text/csv");
    response.setHeader("Content-Disposition", "attachment; file=" + nombreArchivo);

    String[] CSV_HEADER = columnas;
    try (
            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
    ) {
      csvWriter.writeNext(CSV_HEADER);

      for (var obj : lista) {
        String[] persona = new String[obj.length];
        var index = 0;
        for (var valor : obj) {
          persona[index] = String.valueOf(valor);
          index++;
        }
        csvWriter.writeNext(persona);
      }

      log.info("Write CSV using CSVWriter successfully!");
    } catch (Exception e) {
      log.error("Error Exception", e);
    }
  }

  public static void aExcel(List<String[]> lista, String nombreArchivo, String titulo, String nombreHoja, String[] columnas, HttpServletResponse response) {
    // Crear libro de trabajo
    try(var wb = new XSSFWorkbook()){

      response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

      // Crear hoja
      Sheet hoja = wb.createSheet(nombreHoja);

      // Crear fila 1
      Row filaTitulo = hoja.createRow(0);
      Cell celda = filaTitulo.createCell(0);
      celda.setCellValue(titulo);
      hoja.addMergedRegion(new CellRangeAddress(0, 1, 0, columnas.length - 1));

      // Agrega estilos
      CellStyle estilo = wb.createCellStyle();
      Font font = wb.createFont();
      font.setBold(true);
      estilo.setFont(font);

      filaTitulo.getCell(0).setCellStyle(estilo);

      celda.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
      celda.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER);

      estilo.setAlignment(HorizontalAlignment.CENTER);
      estilo.setVerticalAlignment(VerticalAlignment.CENTER);

      // Crear 2 fila
      Row filaData = hoja.createRow(2);

      // Llenar columnas cabeceras en fila 2
      for (int i = 0; i < columnas.length; i++) {
        celda = filaData.createCell(i);
        celda.setCellValue(columnas[i]);
        celda.setCellStyle(estilo);
      }

      // Continúa en la fila 3, para registrar los datos de la lista
      int numFila = 3;
      int posCelda = 0;

      // Llenar filas cuerpo
      for (var obj : lista) {
        filaData = hoja.createRow(numFila);
        for (var valor : obj) {
          filaData.createCell(posCelda).setCellValue(String.valueOf(valor));
          posCelda++;
        }
        posCelda = 0;
        numFila++;
      }

      //AutoSize Columnas
      for (int i = 0; i < columnas.length; i++) {
        hoja.autoSizeColumn(i);
      }

    // Exportar excel
      try(ServletOutputStream out = response.getOutputStream();){
        wb.write(out);
      }
    } catch (IOException e) {
      log.error("Error IOException", e);
    }
  }

}
