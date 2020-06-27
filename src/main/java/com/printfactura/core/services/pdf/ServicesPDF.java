package com.printfactura.core.services.pdf;

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
import com.printfactura.core.services.rocksdb.ServicesCustomer;
import com.printfactura.core.services.rocksdb.ServicesMySelf;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesPDF {

    private final ServicesMySelf mySelf;
    private final ServicesCustomer servicesCustomer;

    public ServicesPDF(ServicesMySelf mySelf, ServicesCustomer servicesCustomer) {
        this.mySelf = mySelf;
        this.servicesCustomer = servicesCustomer;
    }

    public SalesBill ComposeSalesBill(InvoiceNumber invoiceNumber,
                                 FieldsForSearchCustomers fields,
                                 List<RowDetail> lrowDetail,
                                 String AppUserEmail){

        BigDecimal base=BigDecimal.ZERO;
        BigDecimal vat=BigDecimal.ZERO;
        BigDecimal totalBase=BigDecimal.ZERO;
        BigDecimal totalVat=BigDecimal.ZERO;

        MySelf oMysf = mySelf.FindMySelf(AppUserEmail);
        Customer customer = servicesCustomer.FindCustomerByID(AppUserEmail, fields.getId());


        List<DetailSalesBill> detailSalesBills = new ArrayList<>();

        for (RowDetail rd: lrowDetail){

            detailSalesBills.add(
                    DetailSalesBill.builder().Concept(rd.getConcept()).
                            Unit(rd.getUnit()).
                            Price(rd.getPrice()).
                            PorVAT(oMysf.getVat()).
                            build());

            // sum all the rows
             base = rd.getUnit().multiply(rd.getPrice());
             vat = base.multiply(oMysf.getVat()).divide(BigDecimal.valueOf(100));
             totalBase= totalBase.add(base);
             totalVat= totalVat.add(vat);
        }



        /*List<CustomerElectronicAddress> customerElectronicAddresses = new ArrayList<>();

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("James Brennan, sales").
                Type("email").
                Value("James@trilateral-it.com").build());

        customerElectronicAddresses.add(CustomerElectronicAddress.builder().
                Reference("Pamela Perfitt, payments").
                Type("email").
                Value("Pamela@trilateral-it.com").build());*/

         return SalesBill.builder().
                mySelf(oMysf).
                customer(customer).
                headSalesBill(HeadSalesBill.builder().
                        BillNumber(invoiceNumber.getInvoiceNumber()).
                        Date(invoiceNumber.getInvoiceDate()).
                        build()).
                detailSalesBill(detailSalesBills).
                totalSalesBill(TotalSalesBill.builder().
                        BaseEuros(totalBase).
                        VATEuros(totalVat).
                        TotalEuros(totalBase.add(totalVat)).
                        HMRC_ExchangeRates(BigDecimal.valueOf(1.1162)).
                        BasePound(BigDecimal.valueOf(9600/1.1162)).
                        VATPound(BigDecimal.valueOf(1920/1.1162)).
                        TotalPound(BigDecimal.valueOf(11520/1.1162)).
                        build()).
                build();

    }
}
