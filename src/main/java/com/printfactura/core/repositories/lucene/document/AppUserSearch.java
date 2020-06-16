package com.printfactura.core.repositories.lucene.document;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AppUserSearch extends SearchDocument {

   /* public AppUserSearchDocument() throws IOException {
        super("c:/temp/lucene8index/AppUsers");
    }*/

    public TopDocs searchByIdUser(String IdUser, IndexSearcher indexSearcher) throws ParseException, IOException {

        Map<String, Analyzer> analyzerPerField = new HashMap<>();
        analyzerPerField.put("IdUser", new KeywordAnalyzer());
        PerFieldAnalyzerWrapper aWrapper =
                new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);


        QueryParser qp = new QueryParser("IdUser", aWrapper);
        Query idQuery = qp.parse(IdUser);
        TopDocs hits = indexSearcher.search(idQuery, 10);

        return hits;
    }


    public IndexSearcher CreateSearcherAppUsr() throws IOException {

        //indexSearcher=createSearcher("c:/temp/lucene8index/AppUsers");
        Directory dir = FSDirectory.open(Paths.get( "c:/temp/lucene8index/AppUsers"));
        IndexReader reader = DirectoryReader.open(dir);
        // IndexSearcher searcher = new IndexSearcher(reader);

        return indexSearcher = new IndexSearcher(reader);
    }


}
