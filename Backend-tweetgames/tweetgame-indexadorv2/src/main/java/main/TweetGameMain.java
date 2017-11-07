package main;




import MySQLConnection.MySQLDB;
import model.userModel;
import org.apache.lucene.queryparser.classic.ParseException;
import java.io.IOException;
import java.util.List;

import neo4j.neo4j;

import tweetgame_core.tweetgame_core;





public class TweetGameMain {

    public static void main(String[] args) throws IOException, ParseException {


        //1: Inicial controlador recolector e indexador

        tweetgame_core tc = new tweetgame_core();
        tc.beginProcess();


        //2: Inicial controladores de neo4j
        neo4j n4 = new neo4j("bolt://localhost:7687","neo4j","123");
        n4.init(); //Inicializacion de conexion mongo-neo4j
        n4.obtaintopUser();





    }


}
