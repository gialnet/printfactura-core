package com.printfactura.core.domain.sales;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.domain.customer.CustomerElectronicAddress;
import com.printfactura.core.domain.myself.MySelf;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SalesBill {

    MySelf mySelf;
    Customer customer;
    HeadSalesBill headSalesBill;
    List<DetailSalesBill> detailSalesBill;
    TotalSalesBill totalSalesBill;

    public static SalesBill SalesBillBuilder(){

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
                        date(LocalDate.now()).
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
