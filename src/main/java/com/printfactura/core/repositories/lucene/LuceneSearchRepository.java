package com.printfactura.core.repositories.lucene;

import com.printfactura.core.repositories.lucene.document.AppUserSearch;
import com.printfactura.core.repositories.lucene.document.CustomerSearch;
import com.printfactura.core.repositories.lucene.document.InvoiceSearch;
import com.printfactura.core.repositories.lucene.document.SearchDocument;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class LuceneSearchRepository implements LuceneSearchDocuments {

    @Override
    public String CreateIndexFolder(String suuid) {
        return null;
    }

    @Override
    public SearchDocument AppUserSearch() throws IOException {

        return new AppUserSearch();
    }

    @Override
    public SearchDocument CustomerSearch() throws IOException {
        return new CustomerSearch();
    }

    @Override
    public SearchDocument InvoiceSearch() throws IOException {
        return new InvoiceSearch();
    }
}
