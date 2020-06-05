package com.printfactura.core.domain;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class TuplasTotalFactura {
    private final BigDecimal base;
    private final BigDecimal iva;
    private final BigDecimal total;
    private final int id;
    private String stBase;
    private String stIVA;
    private String stTotal;

    public BigDecimal getBase() {
        return base;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public BigDecimal getTotal() {
        return total;
    }


    public int getId() {
        return id;
    }

    public String getStBase() {
        return stBase;
    }

    public String getStIVA() {
        return stIVA;
    }

    public String getStTotal() {
        return stTotal;
    }

    /*
    private TuplasTotalFactura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */

    public static class Builder {

        private BigDecimal base;
        private BigDecimal iva;
        private final int id;

        public Builder(int id) {
            this.id = id;
        }


        public Builder Base(final BigDecimal base) {
            this.base = base;
            return this;
        }

        public Builder Iva(final BigDecimal iva) {
            this.iva = iva;
            return this;
        }

        public TuplasTotalFactura build() {
            return new TuplasTotalFactura(this);
        }
    }

    private TuplasTotalFactura(Builder builder)
    {

        base=builder.base;
        iva=builder.iva;
        id=builder.id;

        // formatear los números para la visualización

        stBase= NumberFormat.getCurrencyInstance(Locale.GERMANY).format(base);

        //
        // calcular el IVA, controlar tipo de IVA 0%
        //

        BigDecimal bg1, bg3;
        bg3= new BigDecimal("100");
        bg1=base;

        // si el IVA es mayor de cero, calcular su importe
        if (!iva.equals(0))
        {
            bg1=bg1.multiply(iva);

            bg1=bg1.divide(bg3);

            total=base.add(bg1);
        }
        else
            total=base;



        stIVA=NumberFormat.getCurrencyInstance(Locale.GERMANY).format(bg1);
        stTotal=NumberFormat.getCurrencyInstance(Locale.GERMANY).format(total);



    }
}
