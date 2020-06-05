package com.printfactura.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Locale;

@Data
@Builder
public class MySelf {

    private final String version="v0.1.0";
    private int cont_facturas;
    private int cont_albaranes;
    private String Nif;
    private String Nombre;
    private String Direccion;
    private String Objeto;
    private String Poblacion;
    private String Pais_ISO3166;
    private String Moneda;
    private String Movil;
    private String fax;
    private String Mail;
    private String url_web;
    private String url_tsa;
    private String perido;
    private String fiscal_year;
    private String fecha_constitucion;
    private BigDecimal irpf_profesionales;
    private BigDecimal irpf_alquileres;
    private BigDecimal iva;
    private String forma_juridica;
    private String TipoBalance="Abreviado";
    private String CriterioDeCaja;
    private BigDecimal carga_impositiva;
    private String Sociedades;
    private int DiasEndMonth;
    private int DiasPeriodo;
    private int DiasEndYear;
    private int NumeroMes;
    private int Doy;
    private String Fecha;
    private String CNAE;
    private String IBAN;
    private String BIC;
    private int tipo_de_cuenta; // 1 free, 2 premiun, 3 enterprise, 4 advisor
    private String EntidadPresenta; //     varchar(4), -- para las domiciliaciones
    private String OficinaPresenta; //     varchar(4), -- para las domiciliaciones
    private String Sufijo; //             varchar(3), -- para las domiciliaciones
    private int periodicidad_er;
    private String EmiteRemesas;
    private String Presupuestos;
    private Locale myLocale=Locale.GERMANY;
}
