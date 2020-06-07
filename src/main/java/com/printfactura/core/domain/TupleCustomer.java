
package com.printfactura.core.domain;

/**
 * Datos de un cliente
 * @author antonio
 */
public class TupleCustomer {

    private final int ID;
    private final int id_customers_type;
    private final String Nif;
    private final String Nombre;
    private final String Direccion;
    private final String Objeto;
    private final String Poblacion;
    private final String Movil;
    private final String Mail;
    private final String IBAN;
    private final String BIC;
    private final String OtrosDatos;
    private final String Domiciliado;
    private final String fecha_orden_sepa;
    private final String referencia_mandato;
    private final byte[] orden_sepa;

    public int getID() {
        return ID;
    }

    public int getId_customers_type() {
        return id_customers_type;
    }
    

    public String getNif() {
        return Nif;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getObjeto() {
        return Objeto;
    }

    public String getPoblacion() {
        return Poblacion;
    }

    
    public String getMovil() {
        return Movil;
    }

    public String getMail() {
        return Mail;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getBIC() {
        return BIC;
    }

    public String getOtrosDatos() {
        return OtrosDatos;
    }

    public byte[] getOrden_sepa() {
        return orden_sepa;
    }

    public String getDomiciliado() {
        return Domiciliado;
    }

    public String getFecha_orden_sepa() {
        return fecha_orden_sepa;
    }

    public String getReferencia_mandato() {
        return referencia_mandato;
    }
    
    

    public static class Builder {

        private final int ID;
        private int id_customers_type=0;
        private String Nif="";
        private String Nombre="";
        private String Direccion="";
        private String Objeto="";
        private String Poblacion="";
        private String Movil="";
        private String Mail="";
        private String IBAN="";
        private String BIC="";
        private String OtrosDatos;
        private byte[] orden_sepa;
        private String Domiciliado;
        private String fecha_orden_sepa;
        private String referencia_mandato;
    
        public Builder(int id) {
            this.ID=id;
        }

        public Builder Id_customers_type(final int id_customers_type) {
            this.id_customers_type = id_customers_type;
            return this;
        }
        
        public Builder Nif(final String Nif) {
            this.Nif = Nif;
            return this;
        }

        public Builder Nombre(final String Nombre) {
            this.Nombre = Nombre;
            return this;
        }

        public Builder Direccion(final String Direccion) {
            this.Direccion = Direccion;
            return this;
        }
        
        public Builder Objeto(final String Objeto) {
            this.Objeto = Objeto;
            return this;
        }
        
        public Builder Poblacion(final String Poblacion) {
            this.Poblacion = Poblacion;
            return this;
        }

        public Builder Movil(final String Movil) {
            this.Movil = Movil;
            return this;
        }

        public Builder Mail(final String Mail) {
            this.Mail = Mail;
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

        public Builder OtrosDatos(final String OtrosDatos) {
            this.OtrosDatos = OtrosDatos;
            return this;
        }
        
        public Builder Orden_sepa(final byte[] orden_sepa) {
            this.orden_sepa = orden_sepa;
            return this;
        }
        
        public Builder Domiciliado(final String Domiciliado) {
            this.Domiciliado = Domiciliado;
            return this;
        }
        
        public Builder Fecha_orden_sepa(final String fecha_orden_sepa) {
            this.fecha_orden_sepa = fecha_orden_sepa;
            return this;
        }
        
        public Builder Referencia_mandato(final String referencia_mandato) {
            this.referencia_mandato = referencia_mandato;
            return this;
        }
        
        public TupleCustomer build() {
            return new TupleCustomer(this);
        }
    }

    private TupleCustomer(Builder builder) {
        this.ID = builder.ID;
        this.Nif = builder.Nif;
        this.Nombre = builder.Nombre;
        this.Direccion = builder.Direccion;
        this.Objeto = builder.Objeto;
        this.Poblacion = builder.Poblacion;
        this.Movil = builder.Movil;
        this.Mail = builder.Mail;
        this.id_customers_type=builder.id_customers_type;
        this.IBAN=builder.IBAN;
        this.BIC=builder.BIC;
        this.OtrosDatos=builder.OtrosDatos;
        this.orden_sepa=builder.orden_sepa;
        this.Domiciliado = builder.Domiciliado;
        this.fecha_orden_sepa=builder.fecha_orden_sepa;
        this.referencia_mandato=builder.referencia_mandato;

    }
}
