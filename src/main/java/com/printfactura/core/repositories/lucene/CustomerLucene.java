package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public interface CustomerLucene {

    IndexSearcher OpenSearcher(String uuid) throws IOException;
    TopDocs searchByIdCode(IndexSearcher indexSearcher, int IdCode) throws ParseException, IOException;
    TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromIdCode, int ToIdCode) throws ParseException, IOException;
    TopDocs searchByCompanyName(IndexSearcher indexSearcher, String CompanyName) throws Exception;
    TopDocs CompanyNamePrefixQuery(IndexSearcher indexSearcher, String StringSearch) throws Exception;

}
