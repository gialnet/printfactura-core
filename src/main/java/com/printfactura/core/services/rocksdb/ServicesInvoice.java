package com.printfactura.core.services.rocksdb;

import com.google.gson.Gson;
import com.printfactura.core.domain.sales.HeadSalesBill;
import com.printfactura.core.domain.sales.SalesBill;
import com.printfactura.core.domain.sales.ui.InvoiceSalesUI;
import com.printfactura.core.repositories.lucene.LuceneWriteRepository;
import com.printfactura.core.repositories.rocksdb.KVRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesInvoice {


    private final KVRepository<String, Object> repository;
    private final LuceneWriteRepository luceneWriteRepository;

    private Gson gson = new Gson();
    private List<InvoiceSalesUI> invoiceSalesUIS = new ArrayList<>();

    public ServicesInvoice(KVRepository<String, Object> repository, LuceneWriteRepository luceneWriteRepository) {
        this.repository = repository;
        this.luceneWriteRepository = luceneWriteRepository;
    }


    /**
     * Get the last number of invoice useful for know in the user interface the inverse order
     *
     * @param email
     * @return
     */
    public int GetSeqInvoice(String email)
    {

        // find sequence
        var sequence = repository.find("sequence.invoice." + email);

        return (int) sequence.get();
    }

    private int IncreaseOneSeqInvoice(String email)
    {

        // find sequence
        var sequence = repository.find("sequence.invoice." + email);
        int seq = (int) sequence.get();
        // increment sequence
        seq++;
        // save sequence
        repository.save("sequence.invoice." + email, seq);

        return seq;
    }

    public boolean SaveSalesBill(SalesBill salesBill,  String AppUserEmail, String uuid) throws IOException, ParseException {

        boolean saveOK=false;

        // RocksDB
        // "sequence.invoice." +  AppUserEmail.toLowerCase()
        int idInvoice = IncreaseOneSeqInvoice(AppUserEmail);

        // save SalesBill object
        HeadSalesBill headSalesBill = salesBill.getHeadSalesBill();
        headSalesBill.setId(idInvoice);
        salesBill.setHeadSalesBill(headSalesBill);

        if (repository.save("sales."+AppUserEmail + salesBill.getHeadSalesBill().getId(),
                gson.toJson(salesBill)) )

            saveOK = luceneWriteRepository.WriteInvoiceDocument(salesBill, uuid);

        return saveOK;
    }

}
