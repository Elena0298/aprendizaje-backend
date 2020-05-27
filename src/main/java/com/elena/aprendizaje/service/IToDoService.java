package com.elena.aprendizaje.service;

import java.util.List;
import java.util.Optional;

import com.elena.aprendizaje.entities.ToDo;

public interface IToDoService {
	public ToDo save(ToDo toDo);
	
	public Optional<ToDo> findById(Long idToDo);
	
	public void deleteById(Long idToDo);
	
	public List<ToDo> getListToDo();
	
	public Optional<ToDo> findByTitle(String title);
}
