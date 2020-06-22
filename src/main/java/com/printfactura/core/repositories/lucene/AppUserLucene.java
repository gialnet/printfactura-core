package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public interface AppUserLucene {

    IndexSearcher OpenSearcher() throws IOException;
    TopDocs searchByIdUser(String IdUser, IndexSearcher indexSearcher) throws ParseException, IOException;

}
