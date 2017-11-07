package cl.citiaps.spring.backend.entities;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cl.citiaps.spring.backend.entities.UserTop;

@Entity
@Table(name="userretop")
@NamedQuery(name="UserReTop.findAll", query="SELECT ur FROM UserReTop ur")
public class UserReTop {
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne
	@JoinColumn(name = "id_top",insertable = false, updatable = false)
	@JsonIgnore
	private UserTop usertop;
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "id_top", unique = false, nullable = false)
	private int id_top;
	
	@Column(name = "user_name",unique = false, nullable = false)
	private String user_name;
	
	@Column(name = "user_url", unique = true, nullable = false)
	private String user_url;
	
	@Column(name = "user_profile_image_url", unique = true, nullable = false)
	private String user_profile_image_url;
	
	public UserReTop(){}
	
	
	public UserTop getUsertop() {
		return usertop;
	}


	public void setUsertop(UserTop usertop) {
		this.usertop = usertop;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_top() {
		return id_top;
	}

	public void setId_top(int id_top) {
		this.id_top = id_top;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	
	
	
	

}
