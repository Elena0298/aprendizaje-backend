package com.elena.aprendizaje.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elena.aprendizaje.daos.IToDoDao;
import com.elena.aprendizaje.entities.ToDo;
import com.elena.aprendizaje.service.IToDoService;

@Service
public class ToDoServiceImpl implements IToDoService{
	
	@Autowired
	private IToDoDao toDao;

	@Override
	public ToDo save(ToDo toDo) {
		return toDao.save(toDo);
	}

	@Override
	public Optional<ToDo> findById(Long idToDo) {
		return toDao.findById(idToDo);
	}

	@Override
	public void deleteById(Long idToDo) {
		toDao.deleteById(idToDo);
	}

	@Override
	public List<ToDo> getListToDo() {
		return toDao.getListToDo();
	}

	@Override
	public Optional<ToDo> findByTitle(String title) {
		return toDao.findByTitle(title);
	}

}
