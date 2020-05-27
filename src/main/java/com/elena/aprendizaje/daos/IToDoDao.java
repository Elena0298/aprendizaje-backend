package com.elena.aprendizaje.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elena.aprendizaje.entities.ToDo;

@Repository
public interface IToDoDao extends CrudRepository<ToDo, Long>{
	
	@Query("SELECT t FROM ToDo t")
	List<ToDo> getListToDo();
	
	Optional<ToDo> findByTitle(String title);
}
