package com.printfactura.core.repositories.lucene.document;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public abstract class SearchDocument<T> {

    protected IndexSearcher indexSearcher;

    /*public SearchDocument(String folder) throws IOException {

        this.indexSearcher = createSearcher(folder);
    }*/

    protected IndexSearcher createSearcher(final String folder) throws IOException {

        Directory dir = FSDirectory.open(Paths.get( folder));
        IndexReader reader = DirectoryReader.open(dir);
       // IndexSearcher searcher = new IndexSearcher(reader);

        return indexSearcher = new IndexSearcher(reader);
    }

    public IndexSearcher getIndexSearcher() throws IOException {
        return this.indexSearcher;
    }
    // public abstract TopDocs searchByIdCode(String s) throws ParseException, IOException;
}
