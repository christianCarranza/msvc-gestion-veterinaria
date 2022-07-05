package org.carranza.java.msvc.veterinaria.msvcgestionproducto.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class FechasUtil {

  private FechasUtil() {
  }

  public static String convertDateToString(Date fecha, String formato) {
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
    String fechaString = "";
    try {
      if (fecha == null) {
        return "";
      }
      fechaString = formatoDelTexto.format(fecha);
    } catch (Exception e) {
      log.info("Error:: convertDateToString"+e.getMessage());
    }
    return fechaString;
  }

  public static String convertDateToString(LocalDateTime fecha, String formato) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern(formato);
    String fechaString = "";
    try {
      if (fecha == null) {
        return "";
      }
      fechaString = fecha.format(fmt);
    } catch (Exception e) {
      log.info("Error:: convertDateToString"+e.getMessage());
    }
    return fechaString;
  }

  public static Date convertStringToDate(String fecha, String formato) {
    SimpleDateFormat format = new SimpleDateFormat(formato);
    try {
      if (StringUtils.isBlank(fecha)) {
        return null;
      }
      return format.parse(fecha);
    } catch (ParseException e) {
      log.info("Error convertStringToDate:: "+e.getMessage());
    }
    return null;
  }

  public static Date getToDay() {
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Calendar cal = Calendar.getInstance();
    format.setCalendar(cal);
    return cal.getTime();
  }


  public static Date getToFullDay() {
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    format.setCalendar(cal);
    return cal.getTime();
  }

  public static LocalDateTime getStringStartDate(String fechaInicial) {
    return LocalDateTime.parse(fechaInicial + "T00:00:00");
  }

  public static LocalDateTime getStringEndDate(String fechaFin) {
    return LocalDateTime.parse(fechaFin + "T23:59:59");
  }

  // Permite comparar si fecha inicial es mayor a fecha final
  public static boolean compareDateInitialFinal(String fechaInicial, String fechaFin, String formato) {
    var r = false;

    try {
      var dateFormat = new SimpleDateFormat(formato);
      Date fechai = dateFormat.parse(fechaInicial);
      Date fechaf = dateFormat.parse(fechaFin);

      if (fechai.after(fechaf)) {
        r = true;
      }
    } catch (ParseException ex) {
      log.info("Erro:: compareDateInitialFinal "+ex.getMessage());
    }

    return r;
  }

  public static LocalDateTime convertDateToLocalDateTime(String fecha) {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate ld = LocalDate.parse(fecha, formato);
    return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
  }

  public static LocalDateTime convertDateToLocalDateTime(String fecha, String formato) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern(formato);
    LocalDate ld = LocalDate.parse(fecha, format);
    return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
  }
  
  public static String validateDateTimeSearch(LocalDateTime fechaInicio, LocalDateTime fechaFin){
    if((fechaInicio != null && fechaFin != null) && fechaInicio.isAfter(fechaFin))
      return "Fecha inicial no debe ser mayor a final";
    if((fechaInicio != null && fechaFin == null) || (fechaFin != null && fechaInicio == null)){
      return "LOS CAMPOS FECHAS DEBEN ESTAR LLENOS CON EL FORMATO yyyy-MM-dd HH:mm:ss";
    }
    return "";
  }

}
