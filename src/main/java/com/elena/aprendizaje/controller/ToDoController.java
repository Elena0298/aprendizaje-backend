package com.elena.aprendizaje.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elena.aprendizaje.component.ToDoComponent;
import com.elena.aprendizaje.entities.ToDo;
import com.elena.aprendizaje.requests.RegisterRequest;
import com.elena.aprendizaje.requests.UpdateRequest;
import com.elena.aprendizaje.responses.GetListToDoResponse;
import com.elena.aprendizaje.responses.RegisterToDoResponse;
import com.elena.aprendizaje.responses.Response;
import com.elena.aprendizaje.responses.UpdateToDoResponse;
import com.elena.aprendizaje.service.IToDoService;

@RestController
public class ToDoController {
	
	@Autowired
	IToDoService toDoServive;
	
	@Autowired
	ToDoComponent toDoComponent;
	
	@PostMapping(value = "registerToDo")
	@CrossOrigin(origins = "*")
	public RegisterToDoResponse registerToDo(@RequestBody RegisterRequest registerRequest) {
		
		RegisterToDoResponse registerResponse = new RegisterToDoResponse();		
		Optional<ToDo> toDoOpcional = toDoServive.findByTitle(registerRequest.getTitle());
		
		if(!toDoOpcional.isPresent()) {
			
			if(toDoComponent.checkAttributes(registerRequest)) {
				ToDo toDo = new ToDo();
				toDo.setTitle(registerRequest.getTitle());
				toDo.setDescription(registerRequest.getDescription());
				
				ToDo toDoSaved = toDoServive.save(toDo);
				
				registerResponse.setSucces(true);
				registerResponse.setMessage("El to do ha sido guardado de manera correcta.");
				registerResponse.setIdToDo(toDoSaved.getIdToDo());
				registerResponse.setTitle(toDoSaved.getTitle());
				registerResponse.setDescription(toDoSaved.getDescription());
			}else {
				registerResponse.setSucces(false);
				registerResponse.setMessage("Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.");
			}
			
		}else {
			registerResponse.setSucces(false);
			registerResponse.setMessage("El titulo que usted quiere usar, ya ha sido registrado anteriormente. Por favor, ingrese un titulo diferente.");
		}		 
		return registerResponse;
	}
	
	@PostMapping(value = "updateToDo")
	@CrossOrigin(origins = "*")
	public UpdateToDoResponse updateToDo(@RequestBody UpdateRequest updateRequest) {
	
		UpdateToDoResponse updateToDoResponse = new UpdateToDoResponse();
		
		if(toDoComponent.checkAttributes(updateRequest)) {
			Optional<ToDo> toDo = toDoServive.findById(updateRequest.getIdToDo());
			Optional<ToDo> toDoOpionalTitle = toDoServive.findByTitle(updateRequest.getTitle());
			
			if(!toDoOpionalTitle.isPresent()) {
				if(toDo.isPresent()) {
					ToDo toDoSave = new ToDo();
					
					toDoSave.setIdToDo(updateRequest.getIdToDo());
					toDoSave.setTitle(updateRequest.getTitle());
					toDoSave.setDescription(updateRequest.getDescription());
					
					ToDo toDoSaved = toDoServive.save(toDoSave);
							
					updateToDoResponse.setTitle(toDoSaved.getTitle());
					updateToDoResponse.setDescription(toDoSaved.getDescription());
					updateToDoResponse.setSucces(true);
					updateToDoResponse.setMessage("El to do ha sido actualizado de manera correcta.");
				}else {
					updateToDoResponse.setSucces(false);
					updateToDoResponse.setMessage("El to do que quiere actualizar, no existe en nuestra base de datos.");
				}
			}else {
				updateToDoResponse.setSucces(false);
				updateToDoResponse.setMessage("El title que quiere utilizar, ya esta siendo utilizado. Por favor registre uno con diferente nombre.");
			}
		}else {
			updateToDoResponse.setSucces(false);
			updateToDoResponse.setMessage("Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.");
		}
		return updateToDoResponse;
	}
	
	@GetMapping(value = "deleteToDo/{idToDo}")
	@CrossOrigin(origins = "*")
	public Response deleteToDo(@PathVariable("idToDo") Long idToDo) {
		
		Response response = new Response();
		Optional<ToDo> opcionalFindId = toDoServive.findById(idToDo);
		
		if(opcionalFindId.isPresent()) {
			if(toDoComponent.checkId(idToDo)) {
				toDoServive.deleteById(idToDo);
				
				response.setSucces(true);
				response.setMessage("El to do se ha borrado de manera exitosa.");
			}else {
				response.setSucces(false);
				response.setMessage("No se puede borrar el toDo porque el idToDo viene vacio o nulo.");
			}	
		}else {
			response.setSucces(false);
			response.setMessage("El toDo que quiere eliminar, no existe en nuestra base de datos.");
		}	
		return response;
	}
	
	@GetMapping(value = "getListToDo")
	@CrossOrigin(origins = "*")
	public GetListToDoResponse getListToDo() {
		
		GetListToDoResponse response = new GetListToDoResponse();
		List<ToDo> toDos = toDoServive.getListToDo();
		
		response.setToDos(toDos);
		response.setMessage("La lista fue devuelta de manera correcta.");
		response.setSucces(true);
			
		return response;
	}
}
