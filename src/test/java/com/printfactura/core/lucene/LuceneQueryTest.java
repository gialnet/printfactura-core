package com.printfactura.core.lucene;

import com.printfactura.core.domain.customer.Customer;
import com.printfactura.core.testutils.OpenIndexLucene;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.InfoStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.file.Paths;

@TestPropertySource(locations="classpath:application-test.properties")
@EnableConfigurationProperties
@SpringBootTest
public class LuceneQueryTest {

    private final String uuid="c743b099-b05e-479d-be21-d39577c09c77";
    private final String email="d1@gmail.com";

    @Test
    public void CustomerFindByName() throws IOException, ParseException {

        // Fields used for sorting have to be indexed and must not be tokenized

        IndexSearcher indexSearcher = OpenIndexLucene.OpenSearcher(uuid);

        System.out.println(indexSearcher.collectionStatistics("CompanyName"));

        Sort sort = new Sort(new SortField("CompanyName", SortField.Type.STRING));

        String WhatAreYouSearchFor="Uni*";

        /*QueryParser qp = new QueryParser("Country", new StandardAnalyzer());
        Query q = 	qp.parse(WhatAreYouSearchFor);
        TopDocs hits = indexSearcher.search(q, 10, sort);*/

        /*Query q = new QueryParser("Country",new StandardAnalyzer()).parse("United");
        TopDocs hits = indexSearcher.search(q,10);*/

        // From Lucene 8 documentation
        QueryParser parser = new QueryParser("Country", new StandardAnalyzer());
        Query query = parser.parse("United");
        ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;

        System.out.println(hits.length);

        PrefixQuery tq = new PrefixQuery(new Term("CompanyName", "T"));

        TopDocs topDocs = indexSearcher.search(tq, 10, sort);

        System.out.println(topDocs.totalHits);

        for (ScoreDoc sd : topDocs.scoreDocs)
        {
            Document d = indexSearcher.doc(sd.doc);
            // System.out.println(String.format(d.get("CompanyName")));

            // Add customer
            System.out.println(d.get("CompanyName"));
            /*System.out.println(Integer.parseInt(d.get("IdCode")));
            System.out.println(d.get("Identification"));
            System.out.println(d.get("Address"));
            System.out.println(d.get("City"));
            System.out.println(d.get("PostCode"));
            System.out.println(d.get("Country"));*/
        }

    }

    @Test
    public void IndexInfo() throws IOException {

        FSDirectory dir = FSDirectory.open(Paths.get("/temp/lucene8index/customer/" + uuid));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        // Create index in Memory RAM
        // MemoryIndex index = new MemoryIndex();
        IndexWriter writer = new IndexWriter(dir, config);

        System.out.println("maxDoc "+writer.getDocStats().maxDoc);
        System.out.println("numDocs "+writer.getDocStats().numDocs);
        writer.close();
    }

}
