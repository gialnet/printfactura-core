package com.printfactura.core.repositories.lucene;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Repository
public class LuceneSearchDocumentsImpl implements LuceneSearchDocuments {

    private final String INDEX_DIR = "c:/temp/lucene8index/";

    @Override
    public String CreateIndexFolder(String suuid) {
        return null;
    }

    public IndexSearcher createSearcher(String folder) throws IOException {

        Directory dir = FSDirectory.open(Paths.get(INDEX_DIR + folder));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        return searcher;
    }

    private void LuceneReadIndex(String suuid) throws Exception {

        IndexSearcher searcher = createSearcher("customer/" + suuid);

        //Search by ID
        TopDocs foundDocs = searchById("1", searcher);

        System.out.println("Total Results :: " + foundDocs.totalHits);

        for (ScoreDoc sd : foundDocs.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("CompanyName")));
        }

        //Search by firstName
        TopDocs foundDocs2 = searchByFirstName("Brian", searcher);

        System.out.println("Total Results :: " + foundDocs2.totalHits);

        for (ScoreDoc sd : foundDocs2.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            System.out.println(String.format(d.get("id")));
        }
    }


    public TopDocs searchById(String IdCode, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("IdCode", new StandardAnalyzer());
        Query idQuery = qp.parse(IdCode);
        TopDocs hits = searcher.search(idQuery, 10);
        return hits;
    }

    public TopDocs searchByFirstName(String firstName, IndexSearcher searcher) throws Exception
    {
        QueryParser qp = new QueryParser("CompanyName", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(firstName);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
    }


}
