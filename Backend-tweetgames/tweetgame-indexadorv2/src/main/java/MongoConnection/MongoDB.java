package MongoConnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.tweetModel;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.stripAccents;

public class MongoDB {


    public MongoDB() {
    }

    public MongoCollection<Document> gettweetsColl(){
        MongoClient client = new MongoClient( "localhost" , 27017 );

        try{
            client.listDatabases();
        }
        catch(MongoException mE)
        {
            System.out.println(mE);
        }

        MongoDatabase mongoDB = client.getDatabase("tweetGamesDB");
        MongoCollection<Document> tweetsColl = mongoDB.getCollection("twitterCollection");

        return tweetsColl;

    }
    /*Retorna una lista de elementos tipo modelo de tweet para ser analizados
    public List<tweetModel> gettweetsMongo(MongoCollection<Document> tweetsColl){

        List<tweetModel> tweets = new ArrayList<tweetModel>();
        MongoCursor<Document> collCursor = tweetsColl.find().iterator();
        while(collCursor.hasNext())
        {
            Document doc = collCursor.next();
            String docId =  doc.get("_id").toString();
            String tweetId = doc.get("id").toString();
            String userId = doc.get("user_id").toString();
            String user_name = doc.get("user_name").toString();
            String text = doc.get("text").toString().toLowerCase();
            text = stripAccents(text);

            tweetModel tm = new tweetModel(docId, tweetId, userId, user_name, text);
            tweets.add(tm);
        }
        return tweets;

    }*/
}
