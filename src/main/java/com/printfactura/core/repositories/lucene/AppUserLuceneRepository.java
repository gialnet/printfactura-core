package com.printfactura.core.repositories.lucene;

import com.printfactura.core.domain.appusers.AppUser;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AppUserLuceneRepository implements AppUserLucene {

    @Value("${lucene-path}")
    private String lucene_path;

    @Override
    public IndexSearcher OpenSearcher() throws IOException {

        //indexSearcher=createSearcher("c:/temp/lucene8index/AppUsers");
        Directory dir = FSDirectory.open(Paths.get( lucene_path+"AppUsers"));
        IndexReader reader = DirectoryReader.open(dir);
        // IndexSearcher searcher = new IndexSearcher(reader);

        return new IndexSearcher(reader);
    }

    @Override
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
}
