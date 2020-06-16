package com.printfactura.core.repositories.lucene.document;

import com.printfactura.core.repositories.lucene.document.SearchDocument;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class InvoiceSearch extends SearchDocument {

   /* public InvoiceSearchDocument(String folder) throws IOException {
        super(folder);
    }*/

    //@Override
    public TopDocs searchByIdCode(String s) throws ParseException, IOException {
        return null;
    }
}
