package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesInvoice {


    private Gson gson = new Gson();
    private List<InvoiceSalesUI> invoiceSalesUIS = new ArrayList<>();

    public List<InvoiceSalesUI> MakeListSalesInvoice(){


        invoiceSalesUIS.clear();

        invoiceSalesUIS.add(InvoiceSalesUI.builder().
                Customer("Trilateral IT").
                DateInvoice("20 May 2020").
                NumberInvoice("2020/05/06").
                VAT("1.600€").
                TotalAmount("9.600€").
                build());

        invoiceSalesUIS.add(InvoiceSalesUI.builder().
                Customer("MBC Consulting").
                DateInvoice("20 May 2019").
                NumberInvoice("2019/08/30").
                VAT("1.200€").
                TotalAmount("8.900€").
                build());

        return invoiceSalesUIS;
    }

}
