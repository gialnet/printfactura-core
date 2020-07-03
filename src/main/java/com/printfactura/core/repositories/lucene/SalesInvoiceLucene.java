package com.printfactura.core.repositories.lucene;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Date;

public interface SalesInvoiceLucene {

    IndexSearcher OpenSearcher(String uuid) throws IOException;

    TopDocs searchByIdCode(IndexSearcher indexSearcher, int InvoiceID)
            throws ParseException, IOException;

    TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromInvoiceID, int ToInvoiceID)
            throws ParseException, IOException;

    TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromInvoiceID, int ToInvoiceID, boolean reverse)
            throws ParseException, IOException;

    TopDocs BetweenDates(IndexSearcher indexSearcher, String FromDate, String ToDate, boolean reverse)
            throws ParseException, IOException, java.text.ParseException;

    TopDocs CompanyNamePrefixQuery(IndexSearcher indexSearcher, String StringSearch) throws Exception;
}
