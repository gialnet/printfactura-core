package com.printfactura.core.repositories.lucene.document;

import com.printfactura.core.domain.appusers.AppUser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Paths;

public class CustomerSearch extends SearchDocument {

   /* public CustomerSearchDocument(String folder) throws IOException {
        super(folder);
    }*/

    @Value("${lucene-path}")
    private String lucene_path;

    //@Override
    public TopDocs searchByIdCode(String IdCode) throws ParseException, IOException {
        QueryParser qp = new QueryParser("IdCode", new StandardAnalyzer());
        Query idQuery = qp.parse(IdCode);
        TopDocs hits = indexSearcher.search(idQuery, 10);

        return hits;
    }

    public TopDocs orderByIdCodeFromTo(String FromIdCode, String ToIdCode) throws ParseException, IOException {

        //SortField sortField = new SortedSetSortField("IdCode",true);

        QueryParser qp = new QueryParser("IdCode", new StandardAnalyzer());
        Query idQuery = qp.parse(FromIdCode);
        TopDocs hits = indexSearcher.search(idQuery, 10, Sort.INDEXORDER, true);

        return hits;
    }

    public TopDocs searchByCompanyName(String CompanyName) throws Exception
    {
        QueryParser qp = new QueryParser("CompanyName", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(CompanyName);
        TopDocs hits = indexSearcher.search(firstNameQuery, 10);
        return hits;
    }

    public IndexSearcher CreateSearcherCustomer(AppUser appUser) throws IOException {

        //indexSearcher=createSearcher("c:/temp/lucene8index/AppUsers");
        Directory dir = FSDirectory.open(Paths.get( lucene_path+"customer/"+appUser.getUserUUID()));
        IndexReader reader = DirectoryReader.open(dir);
        // IndexSearcher searcher = new IndexSearcher(reader);

        return indexSearcher = new IndexSearcher(reader);
    }
}
