package com.printfactura.core;

import com.google.gson.Gson;
import com.printfactura.core.domain.*;
import com.printfactura.core.makePDFinvoice.CreatePDF;
import com.printfactura.core.repositories.GetDataSellBill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class createPDFTest {


    @Autowired
    GetDataSellBill getDataSellBill;

    @Test
    public void makePDFTestOne(){

        CreatePDF createPDF = new CreatePDF(MakeDataOneSellBill());
        
    }

    public DataOneSellBill MakeDataOneSellBill(){

        List<TupleDetailBill> tupleDetailBills = new ArrayList<>();
        tupleDetailBills.add(
                new TupleDetailBill.Builder().concepto("160 hours work").importe("€11,520.00").build());

        return DataOneSellBill.builder().
                mySelf(MySelf.builder().iva(BigDecimal.valueOf(20)).
                        Nombre("Vivaldi-Spring LTD").
                        Direccion("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                        Nif("VAT number: GB 292 4881 67").
                        build()).
                tupleHeadBill(new TupleHeadBill.Builder().Nombre("Trilateral-IT Ltd.").build()).
                tupleDetailBill(tupleDetailBills).
                tupleTotalBill(new TupleTotalBill.Builder().
                        Base(BigDecimal.valueOf(9600)).
                        Iva(BigDecimal.valueOf(1768.28)).build()).
                build();
    }

    @Test
    public void DataOneSellBillTest(){

        Gson gson = new Gson();
        List<TupleDetailBill> tupleDetailBills = new ArrayList<>();
        tupleDetailBills.add(
                new TupleDetailBill.Builder().concepto("160 hours work").importe("€11,520.00").build());

        DataOneSellBill dataOneSellBill = DataOneSellBill.builder().
                mySelf(MySelf.builder().iva(BigDecimal.valueOf(20)).
                        Nombre("Vivaldi-Spring LTD").
                        Direccion("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                        Nif("VAT number: GB 292 4881 67").
                        build()).
                tupleHeadBill(new TupleHeadBill.Builder().Nombre("Trilateral-IT Ltd.").build()).
                tupleDetailBill(tupleDetailBills).
                tupleTotalBill(new TupleTotalBill.Builder().
                        Base(BigDecimal.valueOf(9600)).
                        Iva(BigDecimal.valueOf(1768.28)).build()).
                build();

        System.out.println(dataOneSellBill.getMySelf().getNombre());
        System.out.println(gson.toJson(dataOneSellBill));
        //createPDF mkpdf = new createPDF(getDataSellBill);

    }


}
