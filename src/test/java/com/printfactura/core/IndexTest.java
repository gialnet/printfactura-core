package com.printfactura.core;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//@SpringBootTest
public class IndexTest {

    private static IndexWriter writer;

    @BeforeAll
    public static void SetUp() throws IOException {
        FSDirectory dir = FSDirectory.open(Paths.get("/tmp"));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        // Create index in Memory RAM
        //MemoryIndex writer = new MemoryIndex();
        writer = new IndexWriter(dir, config);
    }

    @Test
    public void OpenSearcher() throws IOException {


        Document document = new Document();

        writer.deleteAll();

        document =  MakeDoc(LocalDate.parse("2020-06-30").toEpochDay());
        writer.addDocument(document);

        document =  MakeDoc(LocalDate.parse("2020-05-12").toEpochDay());
        writer.addDocument(document);

        document =  MakeDoc(LocalDate.parse("2020-01-18").toEpochDay());
        writer.addDocument(document);

        document =  MakeDoc(LocalDate.parse("2020-03-06").toEpochDay());
        writer.addDocument(document);

        document =  MakeDoc(LocalDate.parse("2020-07-01").toEpochDay());


       long iddoc = writer.addDocument(document);

       writer.commit();
       writer.close();
    }

    public Document MakeDoc(Long point){

        Document document = new Document();

        // Date are store like Long values fro fast search and order
        document.add(new LongPoint("DateInvoice", point ));
        document.add(new StoredField("DateInvoiceString", point.toString()) );
        document.add(new StoredField("DateInvoice", point ));
        document.add(new SortedNumericDocValuesField("DateInvoice", point ));

        return document;
    }

    @Test
    public void Search() throws IOException {

        Directory dir = FSDirectory.open(Paths.get( "/tmp"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        Sort sort = new Sort(new SortedNumericSortField("DateInvoice", SortField.Type.LONG, false));

        Query dateQuery = LongPoint.newRangeQuery("DateInvoice",
                LocalDate.parse("2020-01-01").toEpochDay(),
                LocalDate.parse("2020-07-01").toEpochDay()
        );

        TopDocs topDocs = searcher.search( dateQuery, 10, sort, true);

        System.out.println(topDocs.totalHits);

        for (ScoreDoc sd : topDocs.scoreDocs) {
            Document d = searcher.doc(sd.doc);
            System.out.println( d.get("DateInvoiceString") );
            Long datel= Long.valueOf(d.get("DateInvoice"));
            LocalDate loda= LocalDate.ofEpochDay(datel);
            System.out.println(loda);
        }
    }

    @Test
    public void DateToLong(){

        System.out.println(LocalDate.now().minusMonths(1).toEpochDay());
        System.out.println(LocalDate.now().toEpochDay());
    }

    @Test
    public void UpdateDoc() throws IOException {

        Document doc = MakeDoc(LocalDate.parse("2020-05-14").toEpochDay());

        // updateDocument only work with Text fields or StringField
        // for numeric values delete first and the insert
        long result = writer.updateDocument(new Term("DateInvoice",
                String.valueOf(LocalDate.parse("2020-05-12").toEpochDay())), doc );
        System.out.println(result);
        long nd=writer.commit();

        writer.close();
        System.out.println(nd);
    }

    @Test
    public void DeleteDoc() throws IOException {

        long result = writer.deleteDocuments(
                LongPoint.newExactQuery("DateInvoice",
                        LocalDate.parse("2020-05-14").toEpochDay()) );

        System.out.println(result);
        long nd=writer.commit();

        writer.close();
        System.out.println(nd);

    }
}
