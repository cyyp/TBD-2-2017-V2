package cl.citiaps.twitter.streaming;


import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient; 
import com.mongodb.MongoCredential;


public class MongoDB {
	
	private MongoClient mClient;
	private MongoCredential mCredential;//No es necesario
	private MongoDatabase mDatabase;
	private MongoCollection<Document> mCollection;
	
	
	//Get
	public MongoClient getMongoClient()
	{
		return this.mClient;
	}
	
	public MongoCredential getMongoCredential()
	{
		return this.mCredential;
	}
	
	public MongoDatabase getMongoDatabase()
	{
		return this.mDatabase;
	}
	
	public MongoCollection<Document> getMongoCol()
	{
		return this.mCollection;
	}
	
	//Set
	public void setmongoClient(MongoClient mClient)
	{
		this.mClient = mClient;
	}
	
	public void setMongoCredential(MongoCredential mCredential)
	{
		this.mCredential = mCredential;
	}
	
	public void setMongoDatabase(MongoDatabase mDatabase)
	{
		this.mDatabase = mDatabase;
	}
	
	public void setMongoCollection(MongoCollection<Document> mCollection)
	{
		this.mCollection = mCollection;
	}
 
 public MongoDB(){
	 
	 //Creacion cliente mongo
	 this.mClient = new MongoClient( "localhost" , 27017 );
	 //Creacion base de datos, metodo get: si no encuentra la BD indicada crea una con ese nombre.
	 this.mDatabase = this.mClient.getDatabase("tweetGamesDB");
	 //Creacion de una coleccion que almacena los documentos de tweet
	 this.mCollection = this.mDatabase.getCollection("twitterCollection");
	
  
 }
 	

 
}
