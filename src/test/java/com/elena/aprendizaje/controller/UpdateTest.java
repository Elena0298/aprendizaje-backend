package com.elena.aprendizaje.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elena.aprendizaje.requests.UpdateRequest;
import com.elena.aprendizaje.responses.UpdateToDoResponse;
import com.google.gson.Gson;

@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class UpdateTest {
	
MockMvc mvc;
	
	Gson gson;
	
	@Autowired
	WebApplicationContext context;
	
	@BeforeEach
	public void setUpVariables() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		gson = new Gson();
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenAllEnptyFields() throws Exception{
		UpdateRequest update = new UpdateRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		String toDoJson = gson.toJson(update);

		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
	
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals( "Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenTitleIsFull() throws Exception{
		UpdateRequest update = new UpdateRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		update.setTitle("Algo1");
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals("Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenDescriptionIsFull() throws Exception{
		UpdateRequest update = new UpdateRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		update.setDescription("Acá una descripción");
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals("Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenIdToDoIsFull() throws Exception{
		UpdateRequest update = new UpdateRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		update.setIdToDo(1L);
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals("Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfTittleUsedWhenTittleIsInDB() throws Exception{
		UpdateRequest update = new UpdateRequest();
		update.setIdToDo(2L);
		update.setTitle("Algo");
		update.setDescription("Una pequeña descripcion");
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals("El title que quiere utilizar, ya esta siendo utilizado. Por favor registre uno con diferente nombre.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfId_To_DoNoFindWhenId_To_DoIsNotInDB() throws Exception{
		UpdateRequest update = new UpdateRequest();
		update.setIdToDo(3L);
		update.setTitle("Holis");
		update.setDescription("Así está todo acá");
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals("El to do que quiere actualizar, no existe en nuestra base de datos.", updateResponse.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfAllFineWhenFieldsFullAndAllCorrect() throws Exception{
		UpdateRequest update = new UpdateRequest();
		update.setIdToDo(2L);
		update.setTitle("Holis");
		update.setDescription("Así está todo acá");
		
		String toDoJson = gson.toJson(update);
		
		String result = mvc.perform(post("/updateToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		UpdateToDoResponse updateResponse = gson.fromJson(result, UpdateToDoResponse.class);
		assertEquals( "El to do ha sido actualizado de manera correcta.", updateResponse.getMessage());
	}
}
