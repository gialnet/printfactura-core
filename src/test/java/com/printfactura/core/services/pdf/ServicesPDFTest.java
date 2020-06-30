package com.printfactura.core.services.pdf;

import com.printfactura.core.domain.customer.FieldsForSearchCustomers;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.ui.InvoiceNumber;
import com.printfactura.core.domain.sales.ui.RowDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicesPDFTest {

    @Autowired
    ServicesPDF servicesPDF;

    @Test
    void composeSalesBill() {

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

        SalesBill salesBill = servicesPDF.ComposeSalesBill(invoiceNumber,
                                fieldsForSearchCustomers,
                                lrowDetail,
                                "a1@a1");

        assertNotNull(salesBill);
    }

    @Test
    void getInBytePDF() {

    }
}