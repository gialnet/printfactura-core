package com.printfactura.core;

import com.google.gson.Gson;
import com.lowagie.text.DocumentException;
import com.printfactura.core.domain.*;
import com.printfactura.core.domain.customer.CustomerDetail;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.TotalSalesBill;
import com.printfactura.core.makePDFinvoice.CreatePDF;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class createPDFTest {


  /*  @Autowired
    GetDataSellBill getDataSellBill;
*/
    /*@Test
    public void makePDFTestOne() throws SQLException, NamingException, DocumentException, IOException {

        CreatePDF createPDF = new CreatePDF(MakeDataOneSellBill());
        byte[] myPDF = createPDF.doit();
        SaveToFile(myPDF,"myFact.pdf");


    }*/

    public void SaveToFile(byte[] bFile, String fileDest){
        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   /* public SalesBill MakeDataOneSellBill(){

        List<TupleDetailBill> tupleDetailBills = new ArrayList<>();
        tupleDetailBills.add(
                new TupleDetailBill.Builder().concepto("160 hours work").
                        unidades("160").
                        importe("€60.00").
                        total("€9,600.00").build());

        return SalesBill.builder().
                mySelf(MySelf.builder().
                        CompanyName("Vivaldi-Spring LTD").
                        Address("Suite 38, Temple Chambers, 3-7 Temple Avenue").
                        City("London").
                        PostCode("EC4Y 0HP").
                        Country("United Kingdom").
                        Identification("VAT number: GB 292 4881 67").
                        Vat(BigDecimal.valueOf(20)).
                        IBAN("GB50 ABBY 0901 2938 4122 40").
                        BankAccount("SORT CODE 09-01-29 ACCOUNT NUMBER 38412240").
                        BankName("Santander United Kingdom").
                        fiscal_year("2020").
                        TaxPeriod("Q2").
                        build()).
                customerDetail(CustomerDetail.builder().
                        CompanyName("Trilateral-IT LTD").
                        Address("Onega House, 112 Main Road").
                        City("Kent").
                        PostCode("DA14 6NE Sidcup").
                        Country("United Kingdom").
                        Identification("VAT Registration Number 928465196").
                        build()).
                tupleHeadBill(new TupleHeadBill.Builder().
                        Nombre("Trilateral-IT Ltd.").
                        Numero("2020/04/002").
                        IBAN("GB50 ABBY 0901 2938 4122 40").
                        Fecha("30 April 2020").
                        build()).
                tupleDetailBill(tupleDetailBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(9600)).
                        VATEuros(BigDecimal.valueOf(1920)).
                        TotalEuros(BigDecimal.valueOf(11520)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                tupleTotalBill(new TupleTotalBill.Builder().
                        Base(BigDecimal.valueOf(9600)).
                        Iva(BigDecimal.valueOf(20)).build()).
                build();
    }
*/
   /* @Test
    public void DataOneSellBillTest(){

        Gson gson = new Gson();
        List<TupleDetailBill> tupleDetailBills = new ArrayList<>();
        tupleDetailBills.add(
                new TupleDetailBill.Builder().concepto("160 hours work").importe("€11,520.00").build());

        SalesBill salesBill = SalesBill.builder().
                mySelf(MySelf.builder().fiscal_year("2020").TaxPeriod("Q2").
                        build()).
                tupleHeadBill(new TupleHeadBill.Builder().Nombre("Trilateral-IT Ltd.").build()).
                tupleDetailBill(tupleDetailBills).
                tupleTotalBill(new TupleTotalBill.Builder().
                        Base(BigDecimal.valueOf(9600)).
                        Iva(BigDecimal.valueOf(1768.28)).build()).
                build();

        System.out.println(salesBill.getMySelf().getCompanyName());
        System.out.println(gson.toJson(salesBill));
        //createPDF mkpdf = new createPDF(getDataSellBill);

    }*/


}
