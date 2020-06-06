
CREATE SEQUENCE cont_facturas;

CREATE TABLE datosper
(
    id                int  auto_increment,
    tipo_de_cuenta      integer default 1,
    forma_juridica      varchar(60) default 'LTD',
    IBAN                varchar(34), -- los dos primeros digitos indican el país ES codigo para españa
    BIC                 varchar(11), -- puede ser de 8 o de 11 posiciones
    CNAE                varchar(25), -- ejemplo: CNAE J6311: Proceso de datos, hosting y actividades relacionadas o SIC que es el código internacional
    fecha_constitucion  date, -- fecha de constitución de la sociedad
    tipo_actividad      varchar(25) default 'empresarial', -- empresarial o profesional
    carga_impositiva    numeric(4,2) default 20,
    sociedades          varchar(2) default 'NO',
    criterio_de_caja    varchar(2) default 'NO', -- para los autonomos y empresas acogidas al criterio de caja no paga IVA hasta el ingreso
    presupuestos        varchar(2) default 'NO',
    fiscal_year         char(4) default EXTRACT(year FROM now()),
    periodo             char(2) default EXTRACT(QUARTER FROM now()),
    irpf_profesionales  numeric(4,2) default 21,
    irpf_alquileres     numeric(4,2) default 21,
    iva                 numeric(4,2) default 20, -- UK
    otras_reglas        json,
    EntidadPresenta     varchar(4), -- para las domiciliaciones
    OficinaPresenta     varchar(4), -- para las domiciliaciones
    Sufijo              varchar(3)  default '000', -- para las domiciliaciones
    periodicidad_er     integer default 12, -- periodicidad de la emisión de recibos 12 uno por mes,6 cada 2 meses,4 cade tres meses, 2 cada semestre
    EmiteRemesas        varchar(2) default 'NO',
    nif                 char(50) default 'VAT number: GB 292 4881 67',
    nombre              varchar(60) default 'Vivaldi-Spring LTD',
    direccion           varchar(90) default 'Suite 38, Temple Chambers, 3-7 Temple Avenue', -- Avenida Europa, 21
    objeto              varchar(40) default 'London, EC4Y 0HP', -- bloque A 2ºD
    poblacion           varchar(90) default 'United Kingdom', -- 18690 Almuñécar Granada
    Pais_ISO3166        varchar(2) default 'UK',
    movil               varchar(10),
    fax                 varchar(10),
    mail                varchar(90),
    url_web             varchar(250),
    url_tsa             text,
    escrituras_consti   bytea,
    cero36              bytea
);

CREATE TABLE head_bill
(
    id                  int  auto_increment,
    id_cliente          integer,
    id_apunte           integer,
    id_apcobro          integer,
    fiscal_year         char(4),
    trimestre           char(2),
    fecha               date, -- emisión
    fecha_vencimiento   date, -- fecha de pago obligatoria
    fecha_apremio       date, -- para formas jurídicas Sociedades civiles y públicas como comunidades de regantes
    numero              varchar(20), -- 2013/10025
    global_dto          numeric(4,2) default 0, -- para un posible descuento a nivel global de la factura al margen del dto. a cada linea de producto
    estado              varchar(10) DEFAULT 'PENDIENTE',
    fecha_estado        date,
    fecha_cobro         date,
    id_remesa           integer default 0,
    otros_datos         json, -- cajón de sastre para necesidades futuras
    primary key (id)
);

--
-- Lineas de detalle de la factura
--
CREATE TABLE row_bill
(
    id                   int  auto_increment,
    id_bill             integer references head_bill(id), -- bill, invoice
    id_item             integer, -- references productos/items(id), para futuras versiones de clientes con control de almacén
    id_store            integer, -- references almacenes/stores(id), para futuras versiones de clientes con control de almacén
    concepto            varchar(90),
    unidades            numeric(4,2) default 1,
    importe             numeric(8,2) default 0,
    por_vat             numeric(4,2) default 21,
    por_req             numeric(4,2) default 0,
    por_dto             numeric(4,2) default 0,
    primary key (id)
);


--
-- Clientes
--

CREATE TABLE customers
(
    id                      int  auto_increment,
    id_customers_type       integer,
    IBAN                    varchar(34), -- los dos primeros digitos indican el país ES codigo para españa
    BIC                     varchar(11),
    Domiciliado             varchar(15) default 'domiciliado', -- por defecto domiciliado
    nif                     varchar(20),
    nombre                  varchar(60),
    direccion               varchar(90), -- Avenida Europa, 21
    objeto                  varchar(40), -- bloque A 2ºD
    poblacion               varchar(90), -- 18690 Almuñécar Granada
    Pais_ISO3166            varchar(2) default 'ES',
    movil                   varchar(10),
    mail                    varchar(90),
    saldo                   numeric(5),
    passwd                  varchar(40),
    clase                   varchar(2)  DEFAULT 'SL',
    pertenece_a             integer        DEFAULT 0,
    sip                     varchar(40),
    perfil                  varchar(50),
    digitos                 varchar(16),
    rol                     integer,
    carpeta_digitalizacion  varchar(90),
    tipo                    varchar(40)    DEFAULT 'US',
    id_delegacion           integer,
    id_departamento         integer,
    envio_sms               char(1)        DEFAULT 'N',
    databasename            varchar(20),
    passdatabase            varchar(10),
    otros_datos             json,
    CuotaServicio           numeric(8,2) default 0,
    fecha_orden_sepa        date, -- FechaFirmaMandato
    referencia_mandato      varchar(35),
    orden_sepa              bytea, -- orden de domiciliación en formato SEPA
    certificado             bytea,
    primary key (id)
);
--
--
--
-- Vista con los totales agrupados por tipo de IVA

create or replace view total_bill (base,iva, id_bill)
as select sum((importe-((importe*por_dto)/100))*unidades), por_vat, id_bill from row_bill
   group by por_vat,id_bill;

CREATE ALIAS getdto
AS
$$
@CODE
double getDTOval(double base, double dto)
{
if (dto > 0)
    return base * dto / 100;
else
    return 0;
}
$$;

CREATE ALIAS getiva
AS
$$
@CODE
double getVAT(double base, double por_vat)
{

if (por_vat > 0)
    return base * por_vat / 100;
else
    return 0;

}
$$;

--
-- Base imponible de una línea de factura
--
CREATE ALIAS get_BaseImponible
AS
$$
@CODE
double get_BaseImponible(double base, double por_dto, double unidades)
{

double dto=0;
if (dto > 0)
    dto= base * por_dto / 100;

// el importe menos el % de descuento * el número de unidades
return ((base - dto) * unidades);


}
$$;

-- Total a pagar
create or replace view total_a_pagar (total, id_bill) as
select sum(((importe - getdto(importe,por_dto)) * unidades) + getiva(importe-getdto(importe,por_dto), por_vat)) as total,id_bill from row_bill
group by id_bill;

create or replace view vwhead_bill (id,id_cliente,fecha,fecha_vencimiento,fecha_apremio,numero,global_dto,nif,nombre,direccion,objeto,poblacion,movil,mail,
                                    estado,total,id_customers_type,fiscal_year,trimestre, remesa, fecha_mandato, iban , bic, Pais_ISO3166)
as select h.id,h.id_cliente,h.fecha,h.fecha_vencimiento,h.fecha_apremio,h.numero,h.global_dto,c.nif,c.nombre,c.direccion,c.objeto,c.poblacion,c.movil,c.mail,
          h.estado,t.total,c.id_customers_type,h.fiscal_year,h.trimestre,h.id_remesa, c.fecha_orden_sepa,
          c.iban , c.bic, c.Pais_ISO3166
   from head_bill h, customers c, datosper d, total_a_pagar t where h.id_cliente=c.id and d.id=1 and t.id_bill=h.id
   order by h.id;