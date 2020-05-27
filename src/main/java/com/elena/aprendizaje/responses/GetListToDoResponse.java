package com.elena.aprendizaje.responses;

import java.util.List;

import com.elena.aprendizaje.entities.ToDo;

public class GetListToDoResponse extends Response{
	
	private List<ToDo> toDos;

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}
}
