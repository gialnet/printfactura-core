package com.printfactura.core.repositories.lucene;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;

import static org.apache.lucene.document.IntPoint.newExactQuery;
import static org.apache.lucene.document.IntPoint.newRangeQuery;

@Repository
public class SalesInvoiceRepository implements SalesInvoiceLucene {

    @Value("${lucene-path}")
    private String lucene_path;

    @Override
    public IndexSearcher OpenSearcher(String uuid) throws IOException {

        Directory dir = FSDirectory.open(Paths.get( lucene_path+"invoice/"+uuid));
        IndexReader reader = DirectoryReader.open(dir);

        return new IndexSearcher(reader);
    }

    @Override
    public TopDocs searchByIdCode(IndexSearcher indexSearcher, int InvoiceID)
            throws ParseException, IOException {

        Query idQuery = newExactQuery("InvoiceID", InvoiceID);
        TopDocs hits = indexSearcher.search(idQuery, 10);

        return hits;
    }

    @Override
    public TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromInvoiceID, int ToInvoiceID)
            throws ParseException, IOException {

        Sort sort = new Sort(new SortedNumericSortField("InvoiceID", SortField.Type.INT, true));

        Query idQuery = newRangeQuery("InvoiceID",FromInvoiceID, ToInvoiceID);
        TopDocs hits = indexSearcher.search(idQuery, 10, sort, true);

        return hits;
    }

    @Override
    public TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromInvoiceID, int ToInvoiceID, boolean reverse)
            throws ParseException, IOException {

        Sort sort = new Sort(new SortedNumericSortField("InvoiceID", SortField.Type.INT, reverse));

        Query idQuery = newRangeQuery("InvoiceID",FromInvoiceID, ToInvoiceID);
        TopDocs hits = indexSearcher.search(idQuery, 10, sort, true);

        return hits;
    }
}
