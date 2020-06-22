package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;

import static org.apache.lucene.document.IntPoint.newExactQuery;
import static org.apache.lucene.document.IntPoint.newRangeQuery;
import static org.apache.lucene.document.SortedSetDocValuesField.newSlowExactQuery;
import static org.apache.lucene.document.SortedSetDocValuesField.newSlowRangeQuery;

@Repository
public class CustomerLuceneRepository implements CustomerLucene {

    @Value("${lucene-path}")
    private String lucene_path;

    @Override
    public IndexSearcher OpenSearcher(String uuid) throws IOException {
        //indexSearcher=createSearcher("c:/temp/lucene8index/AppUsers");
        Directory dir = FSDirectory.open(Paths.get( lucene_path+"customer/"+uuid));
        IndexReader reader = DirectoryReader.open(dir);
        // IndexSearcher searcher = new IndexSearcher(reader);

        return new IndexSearcher(reader);
    }

    @Override
    public TopDocs searchByIdCode(IndexSearcher indexSearcher, int IdCode) throws ParseException, IOException {

        //QueryParser qp = new newExactQuery("IdCode", new StandardAnalyzer());
        Query idQuery = newExactQuery("IdCode", IdCode);
        TopDocs hits = indexSearcher.search(idQuery, 10);

        return hits;
    }

    @Override
    public TopDocs orderByIdCodeFromTo(IndexSearcher indexSearcher, int FromIdCode, int ToIdCode) throws ParseException, IOException {

        //SortField sortField = new SortedSetSortField("IdCode",true);

        //QueryParser qp = new QueryParser("IdCode", new StandardAnalyzer());
        Query idQuery = newRangeQuery("IdCode",FromIdCode, ToIdCode);
        TopDocs hits = indexSearcher.search(idQuery, 10, Sort.INDEXORDER, true);

        return hits;
    }

    @Override
    public TopDocs searchByCompanyName(IndexSearcher indexSearcher, String CompanyName) throws Exception {

        //QueryParser qp = new QueryParser("CompanyName", new StandardAnalyzer());
        //Query firstNameQuery = 	newSlowExactQuery("CompanyName", new BytesRef(CompanyName) );
        //newSlowRangeQuery
        Sort sort = new Sort(new SortField("CompanyName", SortField.Type.STRING));

        Query firstNameQuery = 	newSlowRangeQuery("CompanyName", new BytesRef(CompanyName),null,true,true );
        TopDocs hits = indexSearcher.search(firstNameQuery, 10, sort);
        return hits;
    }

    @Override
    public TopDocs CompanyNamePrefixQuery(IndexSearcher indexSearcher, String StringSearch) throws Exception {

        Sort sort = new Sort(new SortField("CompanyName", SortField.Type.STRING));

        /*QueryParser qp = new QueryParser("CompanyName", new StandardAnalyzer());
        Query firstNameQuery = 	qp.parse(CompanyName);
        TopDocs hits = indexSearcher.search(firstNameQuery, 10, sort);*/

        PrefixQuery tq = new PrefixQuery(new Term("CompanyName", StringSearch));

        TopDocs hits = indexSearcher.search(tq, 10, sort);

        return hits;
    }
}
