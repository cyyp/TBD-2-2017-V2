package tweetgame_core;


import MongoConnection.MongoDB;
import MySQLConnection.MySQLDB;
import com.mongodb.client.MongoCollection;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.document.Document;
import sentimentAnalyzer.sentimentAnalyzer;
import luceneIndexador.luceneIndexador;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class tweetgame_core {
    private MongoDB mongo;
    private MySQLDB mysqldb;
    private sentimentAnalyzer sentimentanalyzer;
    private luceneIndexador luceneindexador;


    public tweetgame_core() {
        this.mongo = new MongoDB();
        this.mysqldb = new MySQLDB();
        this.sentimentanalyzer = new sentimentAnalyzer();
        MongoCollection<org.bson.Document> mongodbCollection = this.mongo.gettweetsColl();
        this.luceneindexador = new luceneIndexador(mongodbCollection);
        try {
            this.luceneindexador.beginIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<org.apache.lucene.document.Document> getDocsIndexed(String gameName) throws IOException, ParseException {
        gameName = gameName.toLowerCase();
        List<Document> docs = this.luceneindexador.getTweetsRelationated(gameName.toLowerCase());

        return docs;

    }


    public void updateMysql(List<org.apache.lucene.document.Document> docs, String gameName) {
        for (org.apache.lucene.document.Document doc : docs) {
            int condition = this.mysqldb.addTweet(Long.parseLong(doc.get("id")),gameName);
            if(condition == 0 && doc.get("retweet").equals("0")) {

                String valoration = this.sentimentanalyzer.sAnalizer(doc.get("tweet"));
                if (!valoration.equals("neutro")) {
                    try {
                        int[] tweetCount = this.mysqldb.getCountTweets(gameName);
                        if (valoration.equals("positivo")) {
                            this.mysqldb.aumentPositiveTweets(tweetCount, gameName);
                        } else {
                            this.mysqldb.aumentNegativeTweets(tweetCount, gameName);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }

    public void beginProcess() throws IOException, ParseException {
        List<String> titles = this.mysqldb.getGamestitles();
        for(String t: titles){
            if(t.equals("Counter-Strike: Global Offensive"))
            {
                List<org.apache.lucene.document.Document> docsf1 = getDocsIndexed(t);
                List<org.apache.lucene.document.Document> docsf2 = getDocsIndexed("counter-strike: global offensive");
                List<org.apache.lucene.document.Document> docsf3 = getDocsIndexed("csgo");
                List<org.apache.lucene.document.Document> docsf4 = getDocsIndexed("counter-strike");
                List<org.apache.lucene.document.Document> docsf5 = getDocsIndexed("cs:go");
                updateMysql(docsf1,t);
                updateMysql(docsf2,t);
                updateMysql(docsf3,t);
                updateMysql(docsf4,t);
                updateMysql(docsf5,t);
            }
            else if(t.equals("FIFA 18")){
                List<org.apache.lucene.document.Document> docsf1 = getDocsIndexed(t);
                List<org.apache.lucene.document.Document> docsf2 = getDocsIndexed("FIFA18");
                updateMysql(docsf1,t);
                updateMysql(docsf2,t);
            }
            else if(t.equals("PlayerUnknowns Battlegrounds"))
            {
                List<org.apache.lucene.document.Document> docsf1 = getDocsIndexed(t);
                List<org.apache.lucene.document.Document> docsf2 = getDocsIndexed("pubg");
                List<org.apache.lucene.document.Document> docsf3 = getDocsIndexed("Playerunknown's Battlegrounds");
                updateMysql(docsf1,t);
                updateMysql(docsf2,t);
                updateMysql(docsf3,t);
            }
            else if(t.equals("The Legend of Zelda: Breath of the Wild")){
                List<org.apache.lucene.document.Document> docsf1 = getDocsIndexed(t);
                List<org.apache.lucene.document.Document> docsf2 = getDocsIndexed("The legend of zelda");
                List<org.apache.lucene.document.Document> docsf3 = getDocsIndexed("Tloz");
                updateMysql(docsf1,t);
                updateMysql(docsf2,t);
                updateMysql(docsf3,t);
            }
            else if(t.equals("Resident Evil 7: Biohazard")){
                List<org.apache.lucene.document.Document> docsf1 = getDocsIndexed(t);
                List<org.apache.lucene.document.Document> docsf2 = getDocsIndexed("Re7");
                List<org.apache.lucene.document.Document> docsf3 = getDocsIndexed("Re7: Biohazard");
                List<org.apache.lucene.document.Document> docsf4 = getDocsIndexed("Resident Evil 7");
                updateMysql(docsf1,t);
                updateMysql(docsf2,t);
                updateMysql(docsf3,t);
                updateMysql(docsf4,t);

            }
            else {
                List<org.apache.lucene.document.Document> docs = getDocsIndexed(t);
                updateMysql(docs, t);
            }
        }
        this.mysqldb.setTotalTweets();
        this.mysqldb.addDateUpdate();

    }
}