# printfactura-core
Print in PDF format and send the invoice by email for general purpose.

This application follow the main principle of microservices, 
- No external dependencies
- No need configuration
- All if self contain

The application manage object, java objects they are persist into RocksDB in JSON format through Gson Google library.
In the other way go from RocksDB to JSON objects and then java objects with Gson.

For fast text search we use Lucene library the most powerful tool for that sort of task, we implement different type 
of search, prefix, by range. We considere the perfect mariage between RocksDB and Lucene, the join make the force. 





## Java 14
- Spring Boot 2.3.1
- Database [RocksDB 6.8.1](https://rocksdb.org/) for key value
- [Apache Lucene 8.5.2](https://lucene.apache.org/) for text search

- Thymeleaf
- TailwindCCS
- iText
- Gson