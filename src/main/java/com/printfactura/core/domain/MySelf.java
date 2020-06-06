package com.printfactura.core.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MySelf {

    //private final String version="v0.1.0";
    private String Nif;
    private String Nombre;
    private String Direccion;
    private String Objeto;
    private String Poblacion;
    private String Pais_ISO3166;

    private String periodo;
    private String fiscal_year;

    private BigDecimal iva;
    private String IBAN;


}
