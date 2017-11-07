package neo4j;



import MySQLConnection.MySQLDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import model.userModel;
import org.bson.Document;
import org.neo4j.driver.v1.*;
import MongoConnection.MongoDB;

import java.util.ArrayList;
import java.util.List;


public class neo4j {

    Driver driver;
    Session session;
    MongoDB mongo;
    MySQLDB mysqldb;

    public neo4j(String url, String username, String password){
        this.driver = GraphDatabase.driver(url , AuthTokens.basic( username, password) );
        this.session = driver.session();
        this.mongo = new MongoDB();
        this.mysqldb = new MySQLDB();

    }


    public void init(){

        session.run("match (a)-[r]->(b) delete r");
        session.run("match (n) delete n");


        String name1 = "Vladimir";
        String id1 = "123452345";
        String id_o1 = "1234567";
        String name2 = "Jose";
        String id2 = "1231234";
        String id_o2= "123452345";

        //agregar filtro si id original es igual al id del tweet retweeteado
        session.run(String.format("MERGE (a: Twitter_User {name:\"%s\", id:\"%s\", id_orig:\"%s\"})",name1,id1,id_o1)); //MERGE (%s: Person {name:\"%s\", id:\"%s\",  id_orig:\"%s\"}) MERGE (%s) - [rep:REPLY {type:\"reply\", count:1} ] -> (%s)",name1,name1,id1,id_o1,name2,name2,id2,id_o2,name1,name2));
        session.run(String.format("MERGE (a: Twitter_User {name:\"%s\", id:\"%s\", id_orig:\"%s\"})",name2,id2,id_o2));
        //session.run(String.format("MATCH (%s:Twitter_User{name:\"%s\", id:\"%s\", id_orig:\"%s\"})-[retw:RETWEET{type:\"retweet\"}]->(%s:Twitter_User{screen_name:\"%s\"}) )",);
        session.run(String.format("MATCH (a: Twitter_User) where a.id = \"%s\" MATCH (b: Twitter_User) where b.id = \"%s\" CREATE (a)-[r:Retweet]->(b)", id_o2,id2));


        session.close();
        driver.close();

    }


    public void createNodes(){

        MongoCollection<Document> tweetsColl = this.mongo.gettweetsColl();
        MongoCursor<Document> collCursor = tweetsColl.find().iterator();

        this.session.run("match (a)-[r]->(b) delete r");
        this.session.run("match (n) delete n");

        while(collCursor.hasNext()){
            Document doc = collCursor.next();

            String user_name = doc.getString("user_screen_name");
            String user_id = doc.get("user_id").toString();
            String original_user_id = doc.get("original_user_id").toString();
            String retweet = doc.get("retweet").toString();
            String user_mentions = doc.get("user_mentions").toString();
            String user_favourites_count = doc.get("user_favourites_count").toString();
            String followers_count = doc.get("user_followers_count").toString();
            String user_friend_count = doc.get("user_friend_count").toString();
            String user_listed_count = doc.get("user_listed_count").toString();
            String user_statuses_count = doc.get("user_statuses_count").toString();
            String user_url = doc.get("user_url").toString();
            String user_profile_image_url = doc.get("user_profile_image_url").toString();

            String exists = "0";

            StatementResult result = this.session.run(String.format("MATCH (a: Twitter_User) where a.user_id =\"%s\" return 1 as exists",user_id));
            if (result.hasNext()) {
                Record record = result.next();
                exists = record.get("exists").toString();
            }

            if(exists.equals("0")) {
                this.session.run(String.format("MERGE (a: Twitter_User {user_name:\"%s\",user_id:\"%s\",original_user_id:\"%s\",retweet:\"%s\",user_mentions:\"%s\", user_favourites_count:\"%s\",followers_count:\"%s\", user_friend_count:\"%s\",user_listed_count:\"%s\",user_statuses_count:\"%s\",user_url:\"%s\",user_profile_image_url:\"%s\"})", user_name, user_id, original_user_id, retweet, user_mentions, user_favourites_count, followers_count, user_friend_count, user_listed_count, user_statuses_count, user_url, user_profile_image_url));

                if (!user_id.equals(original_user_id) && !original_user_id.equals("0")) {
                    session.run(String.format("MATCH (a: Twitter_User) where a.user_id = \"%s\" MATCH (b: Twitter_User) where b.user_id = \"%s\" CREATE (b)-[r:Retweet]->(a)", original_user_id, user_id));
                }
            }
        }
        this.session.close();
        this.driver.close();
    }

    public void obtaintopUser(){
        //Obtener top 5
        /*MATCH(n:Twitter_User)
        match ()-[r:Retweet]-(n)
        return n, count(r) as rel_count
        order by rel_count desc
        Limit 5*/

        //Obtener hijos del nodo del top
        /*
        MATCH (n:Twitter_User) <-[:Retweet]- (s:Twitter_User) where n.user_id='711971628985376768' return s
         */
        this.mysqldb.truncateTop();

        List<userModel> users = new ArrayList<>();

        StatementResult result = this.session.run("MATCH(n:Twitter_User) MATCH ()-[r:Retweet]->(n) RETURN" +
                " n.user_name as user_name," +
                " n.user_id as user_id," +
                " n.user_mentions as user_mentions," +
                " n.user_favourites_count as user_favourites_count," +
                " n.followers_count as followers_count," +
                " n.user_friend_count as user_friend_count," +
                " n.user_listed_count as user_listed_count," +
                " n.user_statuses_count as user_statuses_count," +
                " n.user_url as user_url," +
                " n.user_profile_image_url as user_profile_image_url" +
                ",count(r) as rel_count order by rel_count desc Limit 10");

        while(result.hasNext()){
            Record record = result.next();
            /*System.out.println(record.get("user_name").toString() + " " + record.get("user_id").toString() + " " + record.get("user_mentions").toString()
            + " " + record.get("user_favourites_count").toString() + " " + record.get("followers_count").toString() + " " + record.get("user_friend_count").toString()
            + " " + record.get("user_listed_count").toString() + " " + record.get("user_statuses_count").toString() + " " + record.get("user_url").toString()
            + " " + record.get("user_url").toString() + " " + record.get("rel_count").toString());*/
            userModel user = new userModel();


            //long uu_id = Long.parseLong(u_id.toString().replaceAll("\"",""));
            user.setU_name(record.get("user_name").toString().replaceAll("\"",""));
            user.setU_id(Long.parseLong(record.get("user_id").toString().replaceAll("\"","")));
            user.setU_mentions(Integer.parseInt(record.get("user_mentions").toString().replaceAll("\"","")));
            user.setU_user_favourites_count(Integer.parseInt(record.get("user_favourites_count").toString().replaceAll("\"","")));
            user.setU_followers_count(Integer.parseInt(record.get("followers_count").toString().replaceAll("\"","")));
            user.setU_friend_count(Integer.parseInt(record.get("user_friend_count").toString().replaceAll("\"","")));
            user.setU_listed_count(Integer.parseInt(record.get("user_listed_count").toString().replaceAll("\"","")));
            user.setU_statuses_count(Integer.parseInt(record.get("user_statuses_count").toString().replaceAll("\"","")));
            user.setU_url(record.get("user_url").toString().replaceAll("\"",""));
            user.setU_profile_image_url(record.get("user_profile_image_url").toString().replaceAll("\"",""));
            user.setRetweet(Integer.parseInt(record.get("rel_count").toString().replaceAll("\"","")));
            user.setRank(0);
            this.mysqldb.insertTopUser(user);
            users.add(user);

            String u_id = record.get("user_id").toString().replaceAll("\"","");

            StatementResult result_b = this.session.run("MATCH (n:Twitter_User) <-[:Retweet]- (s:Twitter_User) where n.user_id = '"+ u_id+"' Return s.user_name as user_name, s.user_url as user_url, s.user_profile_image_url as user_profile_image_url");

            while(result_b.hasNext()){
                Record record_b = result_b.next();
                String user_name = record_b.get("user_name").toString().replaceAll("\"","");
                String user_url = record_b.get("user_url").toString().replaceAll("\"","");
                String user_profile_image_url = record_b.get("user_profile_image_url").toString().replaceAll("\"","");
                this.mysqldb.addRetweet(user.getU_id(),user_name,user_url,user_profile_image_url);


            }



        }
        users.get(0).setRanking(users);
        this.mysqldb.setRankinOnTable(users);

    }


}





