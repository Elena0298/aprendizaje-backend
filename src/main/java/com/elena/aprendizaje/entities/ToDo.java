package com.elena.aprendizaje.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "to_dos")
public class ToDo implements Serializable{
	
	private static final long serialVersionUID = 3299243658423452457L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idToDo;
	
	@Column(unique = true)
	private String title;
	
	private String description;

	public Long getIdToDo() {
		return idToDo;
	}

	public void setIdToDo(Long idToDo) {
		this.idToDo = idToDo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
