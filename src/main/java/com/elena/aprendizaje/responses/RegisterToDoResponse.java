package com.elena.aprendizaje.responses;

public class RegisterToDoResponse extends Response{
	
	private Long idToDo;
	
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
