package cl.citiaps.spring.backend.entities;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;
import cl.citiaps.spring.backend.entities.UserReTop;

@Entity
@Table(name="usertop")
@NamedQuery(name="UserTop.findAll", query="SELECT u FROM UserTop u")
public class UserTop {
	private static final long serialVersionUID = 1L;
	
	
	@OneToMany(mappedBy = "usertop")
	private List<UserReTop> userretop;
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "user_id", unique = true, nullable = false)
	private long user_id;
	
	@Column(name = "user_name", unique = true, nullable = false)
	private String user_name;
	
	@Column(name = "user_mentions", unique = false, nullable = false)
	private int user_mentions;
	
	@Column(name = "user_favourites_count", unique = false, nullable = false)
	private int user_favourites_count;
	
	@Column(name = "followers_count", unique = false, nullable = false)
	private int followers_count;
	
	@Column(name = "user_friend_count", unique = false, nullable = false)
	private int user_friend_count;
	
	@Column(name = "user_listed_count", unique = false, nullable = false)
	private int user_listed_count;
	
	@Column(name = "user_statuses_count", unique = false, nullable = false)
	private int user_statuses_count;
	
	@Column(name = "user_url", unique = true, nullable = false)
	private String user_url;
	
	@Column(name = "user_profile_image_url", unique = true, nullable = false)
	private String user_profile_image_url;
	
	@Column(name = "retweets", unique = false, nullable = false)
	private int retweets;
	
	@Column(name = "rank", unique = true, nullable = false)
	private int rank;

	public UserTop(){}
	
	
	public List<UserReTop> getUserretop() {
		return userretop;
	}


	public void setUserretop(List<UserReTop> userretop) {
		this.userretop = userretop;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_mentions() {
		return user_mentions;
	}

	public void setUser_mentions(int user_mentions) {
		this.user_mentions = user_mentions;
	}

	public int getUser_favourites_count() {
		return user_favourites_count;
	}

	public void setUser_favourites_count(int user_favourites_count) {
		this.user_favourites_count = user_favourites_count;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getUser_friend_count() {
		return user_friend_count;
	}

	public void setUser_friend_count(int user_friend_count) {
		this.user_friend_count = user_friend_count;
	}

	public int getUser_listed_count() {
		return user_listed_count;
	}

	public void setUser_listed_count(int user_listed_count) {
		this.user_listed_count = user_listed_count;
	}

	public int getUser_statuses_count() {
		return user_statuses_count;
	}

	public void setUser_statuses_count(int user_statuses_count) {
		this.user_statuses_count = user_statuses_count;
	}

	public String getUser_url() {
		return user_url;
	}

	public void setUser_url(String user_url) {
		this.user_url = user_url;
	}

	public String getUser_profile_image_url() {
		return user_profile_image_url;
	}

	public void setUser_profile_image_url(String user_profile_image_url) {
		this.user_profile_image_url = user_profile_image_url;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}


	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}
	
	
	
}
