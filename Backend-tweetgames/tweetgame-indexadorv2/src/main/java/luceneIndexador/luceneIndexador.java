package luceneIndexador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.tweetModel;
import MongoConnection.MongoDB;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.stripAccents;


public class luceneIndexador {

    //private List<tweetModel> tweets;
    private MongoCollection<org.bson.Document> tweets_m;
    private File directory;

    public luceneIndexador(MongoCollection<org.bson.Document> tweets){
        this.tweets_m = tweets;
        this.directory = new File("/home/juanpablo/Escritorio/USACH/TBD/Backend/index_files");

    }


    public void beginIndex() throws IOException {

        Analyzer analyzer = new SpanishAnalyzer(Version.LUCENE_46);

        Directory dir = FSDirectory.open(this.directory);

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,analyzer);

        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter w = new IndexWriter(dir, config);


        Document document;

        MongoCursor<org.bson.Document> collCursor = this.tweets_m.find().iterator();
        while(collCursor.hasNext())
        {
            document = new Document();
            org.bson.Document doc = collCursor.next();
            document.add(new TextField("tweet",stripAccents(doc.get("text").toString().toLowerCase()),Field.Store.YES));
            document.add(new StringField("id",doc.get("id").toString(), Field.Store.YES));
            document.add(new StringField("retweet",doc.get("original_user_id").toString(), Field.Store.YES));
            w.addDocument(document);
        }



        w.close();
    }


    public List<Document> getTweetsRelationated(String querystr) throws IOException, ParseException {

        querystr = querystr.toLowerCase();
        List<Document> tweets = new ArrayList<>();

        //Analyzer analyzer = new SpanishAnalyzer(Version.LUCENE_46);
        Directory dir = FSDirectory.open(this.directory);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);

        //QueryParser query_p = new QueryParser(Version.LUCENE_46,"tweet",analyzer);

        String[] querywords = querystr.split(" ");

        //2 o mas palabras
        if(querywords.length > 1){

            PhraseQuery phraseQuery = new PhraseQuery();
            for(String word:querywords){
                phraseQuery.add(new Term("tweet",word));
            }
            TopDocs docs = searcher.search(phraseQuery,1000);
            ScoreDoc[] hits = docs.scoreDocs;
            for(int i = 0; i < hits.length; i++){
                Document document = searcher.doc(hits[i].doc);
                tweets.add(document);
            }
        }
        //solo 1 palabra
        else{
            WildcardQuery wildcardQuery = new WildcardQuery(new Term("tweet","*"+querystr+"*"));
            TopDocs docs = searcher.search(wildcardQuery,1000);
            ScoreDoc[] hits = docs.scoreDocs;
            for(int i = 0; i < hits.length; i++){
                Document document = searcher.doc(hits[i].doc);
                tweets.add(document);
            }

            TermQuery tquery = new TermQuery(new Term("tweet",querystr));
            TopDocs docst = searcher.search(tquery,1000);
            ScoreDoc[] thits = docst.scoreDocs;
            for(int i = 0; i < thits.length; i++){
                Document document = searcher.doc(thits[i].doc);
                tweets.add(document);
            }




        }

        reader.close();
        return tweets;




    }




}
