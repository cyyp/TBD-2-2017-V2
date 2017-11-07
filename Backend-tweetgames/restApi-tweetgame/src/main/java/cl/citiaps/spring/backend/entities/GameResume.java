package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cl.citiaps.spring.backend.entities.Game;

@Entity
@Table(name="resume")
@NamedQuery(name="GameResume.findAll", query="SELECT r FROM GameResume r")
public class GameResume {
	private static final long serialVersionUID = 1L;
	
	
	@OneToOne
	@JoinColumn(name = "id_juego",insertable = false, updatable = false)
	@JsonIgnore
	private Game game;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "id_juego", unique = true, nullable = false)
	private int id_juego;	
	
	@Column(name ="id_ciudad",unique = false, nullable = false)
	private int id_ciudad;
	
	@Column(name = "tweets_pos", unique = false, nullable = false)
	private int tweets_pos;
	
	@Column(name="tweets_neg", unique = false, nullable = false)
	private int tweets_neg;

	@Column(name = "total_tweets", unique = false, nullable = false)
	private int total_tweets;
	
	public GameResume(){
		
	}
	
	public Game getGame(){
		return game;
	}
	
	public int getId_resume() {
		return id;
	}

	public void setId_resume(int id_resume) {
		this.id = id_resume;
	}
	
	public int getId_game() {
		return id_juego;
	}
	
	public void setId_game(int id_juego) {
		this.id_juego = id_juego;
	}

	public int getId_ciudad() {
		return id_ciudad;
	}

	public void setId_ciudad(int id_ciudad) {
		this.id_ciudad = id_ciudad;
	}

	public int getTweets_pos() {
		return tweets_pos;
	}

	public void setTweets_pos(int tweets_pos) {
		this.tweets_pos = tweets_pos;
	}

	public int getTweets_neg() {
		return tweets_neg;
	}

	public void setTweets_neg(int tweets_neg) {
		this.tweets_neg = tweets_neg;
	}
	
	public int getTotal_tweets() {
		return total_tweets;
	}

	public void setTotal_tweets(int total_tweets) {
		this.total_tweets = total_tweets;
	}
	
	
}
