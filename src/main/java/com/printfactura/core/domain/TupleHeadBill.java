package com.printfactura.core.domain;

public class TupleHeadBill {

    private final int id;
    private final int id_cliente;
    private final int id_customers_type;
    private final String fecha;
    private final String fecha_vencimiento;
    private final String fecha_apremio;
    private final String numero;
    private final String global_dto;
    private final String nif;
    private final String nombre;
    private final String direccion;
    private final String objeto;
    private final String poblacion;
    private final String pais_iso3166;
    private final String movil;
    private final String mail;
    private final String IBAN;
    private final String BIC;
    private final String fecha_mandato;
    private final String estado;
    private final String total;
    private final int id_remesa;
    private final String ConceptoRemesa;

    public String getEstado() {
        return estado;
    }

    public String getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public int getId_customers_type() {
        return id_customers_type;
    }


    public String getFecha() {
        return fecha;
    }

    public String getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public String getFecha_apremio() {
        return fecha_apremio;
    }


    public String getNumero() {
        return numero;
    }

    public String getGlobal_dto() {
        return global_dto;
    }

    public String getNif() {
        return nif;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMovil() {
        return movil;
    }

    public String getMail() {
        return mail;
    }

    public String getObjeto() {
        return objeto;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public String getIBAN() {
        return IBAN;
    }

    public int getId_remesa() {
        return id_remesa;
    }


    public String getConceptoRemesa() {

        if (ConceptoRemesa.length()<140)
            return ConceptoRemesa;
        else
            return ConceptoRemesa.substring(0, 69);

    }

    public String getPais_iso3166() {
        return pais_iso3166;
    }

    public String getBIC() {
        return BIC;
    }

    public String getFecha_mandato() {
        return fecha_mandato;
    }




    public static class Builder {

        // vista SQL vwhead_bill
        private int id;
        private int id_cliente=0;
        private int id_customers_type=0;
        private String fecha="";
        private String fecha_vencimiento="";
        private String fecha_apremio="";
        private String numero="";
        private String global_dto="";
        private String nif="";
        private String nombre="";
        private String direccion="";
        private String objeto="";
        private String poblacion="";
        private String pais_iso3166="ES";
        private String movil="";
        private String mail="";
        private String IBAN;
        private String BIC;
        private String fecha_mandato;
        private String estado="";
        private String total="";
        private int id_remesa=0;
        private String ConceptoRemesa="Cuota Comunidad de Propietarios"; // 140

        public Builder() {
            super();
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder Id_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
            return this;
        }

        public Builder Id_customers_type(int id_customers_type) {
            this.id_customers_type = id_customers_type;
            return this;
        }

        public Builder Fecha(final String fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder Fecha_vencimiento(final String fecha_vencimiento) {
            this.fecha_vencimiento = fecha_vencimiento;
            return this;
        }

        public Builder Fecha_apremio(final String fecha_apremio) {
            this.fecha_apremio = fecha_apremio;
            return this;
        }
        public Builder Numero(final String numero) {
            this.numero = numero;
            return this;
        }

        public Builder Global_dto(final String global_dto) {
            this.global_dto = global_dto;
            return this;
        }

        public Builder Nif(final String nif) {
            this.nif = nif;
            return this;
        }

        public Builder Nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder Direccion(final String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder Objeto(final String objeto) {
            this.objeto = objeto;
            return this;
        }

        public Builder Poblacion(final String poblacion) {
            this.poblacion = poblacion;
            return this;
        }

        public Builder Pais_iso3166(final String pais_iso3166) {
            this.pais_iso3166 = pais_iso3166;
            return this;
        }

        public Builder Movil(final String movil) {
            this.movil = movil;
            return this;
        }

        public Builder Mail(final String mail) {
            this.mail = mail;
            return this;
        }

        public Builder IBAN(final String IBAN) {
            this.IBAN = IBAN;
            return this;
        }

        public Builder BIC(final String BIC) {
            this.BIC = BIC;
            return this;
        }

        public Builder Fecha_mandato(final String fecha_mandato) {
            this.fecha_mandato = fecha_mandato;
            return this;
        }

        public Builder Estado(final String estado) {
            this.estado = estado;
            return this;
        }

        public Builder Total(final String total) {
            this.total = total;
            return this;
        }

        public Builder Id_remesa(final int id_remesa) {
            this.id_remesa = id_remesa;
            return this;
        }
        // ConceptoRemesa 140 maximo
        public Builder ConceptoRemesa(final String ConceptoRemesa) {
            this.ConceptoRemesa = ConceptoRemesa;
            return this;
        }

        public TupleHeadBill build() {
            return new TupleHeadBill(this);
        }
    }

    private TupleHeadBill(Builder builder)
    {
        this.id=builder.id;
        this.id_cliente=builder.id_cliente;
        this.id_customers_type=builder.id_customers_type;
        this.fecha=builder.fecha;
        this.fecha_vencimiento=builder.fecha_vencimiento;
        this.fecha_apremio=builder.fecha_apremio;
        this.numero=builder.numero;
        this.global_dto=builder.global_dto;
        this.nif=builder.nif;
        this.nombre=builder.nombre;
        this.direccion=builder.direccion;
        this.objeto=builder.objeto;
        this.poblacion=builder.poblacion;
        this.pais_iso3166=builder.pais_iso3166;
        this.movil=builder.movil;
        this.mail=builder.mail;
        this.IBAN=builder.IBAN;
        this.BIC=builder.BIC;
        this.fecha_mandato=builder.fecha_mandato;
        this.estado=builder.estado;
        this.total=builder.total;
        this.id_remesa=builder.id_remesa;
        this.ConceptoRemesa=builder.ConceptoRemesa;

    }
}
