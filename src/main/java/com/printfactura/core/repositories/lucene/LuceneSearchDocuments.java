package com.printfactura.core.repositories.lucene;

import com.printfactura.core.repositories.lucene.document.SearchDocument;
import org.springframework.stereotype.Repository;

import java.io.IOException;

public interface LuceneSearchDocuments <T extends SearchDocument> {


    String CreateIndexFolder(String suuid);

    T AppUserSearch() throws IOException;
    T CustomerSearch() throws IOException;
    T InvoiceSearch() throws IOException;
}
