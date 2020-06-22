package com.printfactura.core.services.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import com.printfactura.core.repositories.lucene.AppUserLucene;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LuceneServiceAppUser {

    private final AppUserLucene appUserLucene;

    //private AppUserSearchDocument appUserSearchDocument;
    private AppUser appUser;

    public LuceneServiceAppUser(AppUserLucene appUserLucene) {
        this.appUserLucene = appUserLucene;
    }


    public AppUser searchByIdUser(String IdUser) throws IOException, ParseException {


        IndexSearcher searcher = appUserLucene.OpenSearcher();

        TopDocs docFound = appUserLucene.searchByIdUser(IdUser, searcher);


        for (ScoreDoc sd : docFound.scoreDocs) {

            Document d = searcher.doc(sd.doc);
            appUser = AppUser.builder().
                    IdUser(d.get("IdUser")).
                    UserUUID(d.get("UserUUID")).
                    Status(d.get("Status")).
                    build();
        }

        return appUser;
    }
}
