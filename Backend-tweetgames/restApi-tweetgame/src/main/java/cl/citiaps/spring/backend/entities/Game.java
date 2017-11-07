package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="juegos")
@NamedQuery(name="Game.findAll", query="SELECT j FROM Game j")
public class Game implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy = "game")
	private GameResume gameresume;
	
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name ="nombre",unique = true, nullable = false)
	private String nombre;
	
	@Column(name = "desarrollador", unique = false, nullable = false)
	private String desarrollador;
	
	@Column(name="fecha_lanzamiento",unique = false, nullable=false)
	private Timestamp fecha_lanzamiento;
	
	@Column(name = "genero",unique = false,nullable=false)
	private String genero;

	
	public Game(){
	
	}
	public GameResume getResume(){
		return gameresume;
	}
	
	public int getGame_id() {
		return id;
	}

	public void setGame_id(int id) {
		this.id = id;
	}

	public String getGame_name() {
		return nombre;
	}

	public void setGame_name(String nombre) {
		this.nombre = nombre;
	}

	public String getDesarrollador() {
		return desarrollador;
	}

	public void setDesarrollador(String desarrollador) {
		this.desarrollador = desarrollador;
	}

	public Timestamp getFecha_lanzamiento() {
		return fecha_lanzamiento;
	}

	public void setFecha_lanzamiento(Timestamp fecha_lanzamiento) {
		this.fecha_lanzamiento = fecha_lanzamiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
}
