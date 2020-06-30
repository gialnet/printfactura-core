package com.printfactura.core.testutils;

import com.printfactura.core.domain.MySelf;
import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.CustomerElectronicAddress;
import com.printfactura.core.domain.customer.FieldsForSearchCustomers;
import com.printfactura.core.domain.sales.DetailSalesBill;
import com.printfactura.core.domain.sales.HeadSalesBill;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.TotalSalesBill;
import com.printfactura.core.domain.sales.ui.InvoiceNumber;
import com.printfactura.core.domain.sales.ui.RowDetail;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        List<CustomerElectronicAddress> customerElectronicAddresses = new ArrayList<>();

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("James Brennan, sales").
                Type("email").
                Value("James@trilateral-it.com").build());

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("Pamela Perfitt, payments").
                Type("email").
                Value("Pamela@trilateral-it.com").build());

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
                        //customerElectronicAddresses(customerElectronicAddresses).
                                build()).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber("2020/03/01").
                        Date(ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)).
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
}
