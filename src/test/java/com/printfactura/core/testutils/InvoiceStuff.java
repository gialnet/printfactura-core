package com.printfactura.core.testutils;

import com.printfactura.core.domain.myself.MySelf;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.FieldsForSearchCustomers;
import com.printfactura.core.domain.sales.DetailSalesBill;
import com.printfactura.core.domain.sales.HeadSalesBill;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.TotalSalesBill;
import com.printfactura.core.domain.sales.ui.InvoiceNumber;
import com.printfactura.core.domain.sales.ui.RowDetail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceStuff {

    public static SalesBill FromInvoiceBlocks(){


        InvoiceNumber invoiceNumber = InvoiceNumber.builder().
                invoiceDate("2020/06/30").
                invoiceNumber("2020/154").
                build();

        FieldsForSearchCustomers fieldsForSearchCustomers = FieldsForSearchCustomers.builder().
                searchName("Customer 1").
                id(1).
                build();

        List<RowDetail> lrowDetail = new ArrayList<>();

        lrowDetail.add(RowDetail.builder().
                Unit(BigDecimal.valueOf(160)).
                Price(BigDecimal.valueOf(60)).
                VAT(BigDecimal.valueOf(20)).
                Concept("Hours Work in month of May").
                build());

        return null;
    }

    public static SalesBill createSalesBill(){
        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("160 hours work").
                        Unit(BigDecimal.valueOf(160)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());

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
                customer(Customer.builder().
                        CompanyName("Trilateral-IT LTD").
                        Address("Onega House, 112 Main Road").
                        City("Kent").
                        PostCode("DA14 6NE Sidcup").
                        Country("United Kingdom").
                        Identification("VAT Registration Number 928465196").
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/03/01").
                        Date(LocalDate.parse("2020-01-15").toString()).
                        total(BigDecimal.valueOf(1500)).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(9600)).
                        VATEuros(BigDecimal.valueOf(1920)).
                        TotalEuros(BigDecimal.valueOf(11520)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }

    public static SalesBill createSalesBill2(){
        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("150 hours work").
                        Unit(BigDecimal.valueOf(150)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());


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
                customer(Customer.builder().
                        CompanyName("Company 2").
                        Address("Street 2").
                        City("Slazburgo").
                        PostCode("87502").
                        Country("Austria").
                        Identification("928465196").
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/04/31").
                        Date(LocalDate.parse("2020-02-26").toString()).
                        total(BigDecimal.valueOf(2400)).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(2400)).
                        VATEuros(BigDecimal.valueOf(2420)).
                        TotalEuros(BigDecimal.valueOf(2420)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }

    public static SalesBill createSalesBill3(){
        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("140 hours work").
                        Unit(BigDecimal.valueOf(140)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());

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
                customer(Customer.builder().
                        CompanyName("Toptal").
                        Address("Main house court").
                        City("Oregon").
                        PostCode("78996").
                        Country("USA").
                        Identification("928465196").
                        //customerElectronicAddresses(customerElectronicAddresses).
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/04/101").
                        Date(LocalDate.parse("2020-03-30").toString()).
                        total(BigDecimal.valueOf(19600)).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(19600)).
                        VATEuros(BigDecimal.valueOf(11920)).
                        TotalEuros(BigDecimal.valueOf(111520)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }

    public static SalesBill createSalesBill4(){
        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("130 hours work").
                        Unit(BigDecimal.valueOf(130)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());

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
                customer(Customer.builder().
                        CompanyName("BMW Group").
                        Address("Magnusen Strase").
                        City("Munich").
                        PostCode("82457").
                        Country("Germany").
                        Identification("BB 928465196").
                        //customerElectronicAddresses(customerElectronicAddresses).
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/05/551").
                        Date(LocalDate.parse("2020-04-30").toString()).
                        total(BigDecimal.valueOf(22500)).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(2200)).
                        VATEuros(BigDecimal.valueOf(2220)).
                        TotalEuros(BigDecimal.valueOf(22520)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }

    public static SalesBill createSalesBill5(){
        List<DetailSalesBill> detailSalesBills = new ArrayList<>();
        detailSalesBills.add(
                DetailSalesBill.builder().Concept("120 hours work").
                        Unit(BigDecimal.valueOf(120)).
                        Price(BigDecimal.valueOf(60)).
                        PorVAT(BigDecimal.valueOf(20)).build());

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
                customer(Customer.builder().
                        CompanyName("Trilateral-IT LTD").
                        Address("Onega House, 112 Main Road").
                        City("Kent").
                        PostCode("DA14 6NE Sidcup").
                        Country("United Kingdom").
                        Identification("VAT Registration Number 928465196").
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/07/07").
                        Date(LocalDate.parse("2020-05-26").toString()).
                        total(BigDecimal.valueOf(8820)).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(BigDecimal.valueOf(8800)).
                        VATEuros(BigDecimal.valueOf(8820)).
                        TotalEuros(BigDecimal.valueOf(811520)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }
}
