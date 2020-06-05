package com.printfactura.core.domain;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public enum TuplasLineasFactura {

    private final String id;
    private final String id_bill;
    private final String id_item;
    private final String id_store;
    private final String concepto;
    private final String unidades;
    private final String importe;
    private final String por_vat;
    private final String por_dto;
    private final String por_req;
    private final String total;

    public String getId() {
        return id;
    }

    public String getId_bill() {
        return id_bill;
    }

    public String getId_item() {
        return id_item;
    }

    public String getId_store() {
        return id_store;
    }

    public String getConcepto() {
        return concepto;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getImporte() {
        return importe;
    }

    public String getPor_vat() {
        return por_vat;
    }

    public String getPor_dto() {
        return por_dto;
    }

    public String getPor_req() {
        return por_req;
    }


    public String getTotal() {
        return total;
    }


    public static class Builder {

        private final String id;
        private String id_bill="0";
        private String id_item="0";
        private String id_store="0";
        private String concepto="";
        private String unidades="1";
        private String importe="0";
        private String por_vat="21";
        private String por_dto="0";
        private String por_req="0";
        private String total="0";

        public Builder(String id)
        {
            this.id=id;

        }

        public Builder id_bill(String valor)
        {
            this.id_bill=valor;
            return this;
        }

        public Builder id_item(String valor)
        {
            this.id_item=valor;
            return this;
        }
        public Builder id_store(String valor)
        {
            this.id_store=valor;
            return this;
        }
        public Builder concepto(String valor)
        {
            this.concepto=valor;
            return this;
        }
        public Builder unidades(String valor)
        {
            this.unidades=valor;
            return this;
        }
        public Builder importe(String valor)
        {
            this.importe=valor;
            return this;
        }
        public Builder por_vat(String valor)
        {
            this.por_vat=valor;
            return this;
        }
        public Builder por_dto(String valor)
        {
            this.por_dto=valor;
            return this;
        }
        public Builder por_req(String valor)
        {
            this.por_req=valor;
            return this;
        }

        public Builder total(String valor)
        {
            this.total=valor;
            return this;
        }

        public TuplasLineasFactura build() {

            return new TuplasLineasFactura(this);
        }

        public TuplasLineasFactura build(Locale formato) {

            return new TuplasLineasFactura(this, formato);
        }
    }

    private TuplasLineasFactura(Builder builder) {
        this.id=builder.id;
        this.id_bill=builder.id_bill;
        this.id_item=builder.id_item;
        this.id_store=builder.id_store;
        this.concepto=builder.concepto;
        this.unidades=builder.unidades;  //
        this.importe=builder.importe;    //
        this.por_vat=builder.por_vat;    //
        this.por_dto=builder.por_dto;    //
        this.por_req=builder.por_req;
        this.total=builder.total;        //
    }

    private TuplasLineasFactura(Builder builder, Locale formato) {

        this.id=builder.id;
        this.id_bill=builder.id_bill;
        this.id_item=builder.id_item;
        this.id_store=builder.id_store;
        this.concepto=builder.concepto;
        this.unidades=builder.unidades;

        BigDecimal bg= new BigDecimal(builder.importe);
        this.importe= NumberFormat.getCurrencyInstance(formato).format(bg);

        bg = new BigDecimal(builder.por_vat);

        this.por_vat=NumberFormat.getNumberInstance(formato).format(bg);
        this.por_dto=builder.por_dto;

        bg = new BigDecimal(builder.por_req);
        this.por_req=NumberFormat.getNumberInstance(formato).format(bg);

        bg = new BigDecimal(builder.total);
        this.total=NumberFormat.getCurrencyInstance(formato).format(bg);


        bg=null;
    }
}
