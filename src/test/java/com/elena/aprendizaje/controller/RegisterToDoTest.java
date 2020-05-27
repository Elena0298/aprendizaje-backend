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

import com.elena.aprendizaje.requests.RegisterRequest;
import com.elena.aprendizaje.responses.RegisterToDoResponse;
import com.google.gson.Gson;

@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class RegisterToDoTest {
	
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
		RegisterRequest register = new RegisterRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		String toDoJson = gson.toJson(register);
		
		//Se ejecuta método REST en RestController
		String result = mvc.perform(post("/registerToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		//Se obtiene el resultado del método REST
		RegisterToDoResponse registerResponse = gson.fromJson(result, RegisterToDoResponse.class);
		assertEquals(registerResponse.getMessage(), "Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.");
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenTitleIsFull() throws Exception{
		RegisterRequest register = new RegisterRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		register.setTitle("Algo1");
		
		String toDoJson = gson.toJson(register);
		
		String result = mvc.perform(post("/registerToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RegisterToDoResponse registerResponse = gson.fromJson(result, RegisterToDoResponse.class);
		assertEquals(registerResponse.getMessage(), "Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.");
	}
	
	@Test
	public void itShouldReturnMessageOfEnptyFieldsWhenDescriptionIsFull() throws Exception{
		RegisterRequest register = new RegisterRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		register.setDescription("Acá una descripción");
		
		String toDoJson = gson.toJson(register);
		
		String result = mvc.perform(post("/registerToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RegisterToDoResponse registerResponse = gson.fromJson(result, RegisterToDoResponse.class);
		assertEquals(registerResponse.getMessage(), "Uno o varios de los campos estan vacios o nulos. Por favor, llena todos los campos.");
	}
	
	@Test
	public void itShouldReturnMessageOfAllFineWhenDescriptionAndTitleAreFull() throws Exception{
		RegisterRequest register = new RegisterRequest();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		register.setTitle("Un título");
		register.setDescription("Acá una descripción");
		
		String toDoJson = gson.toJson(register);
		
		String result = mvc.perform(post("/registerToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RegisterToDoResponse registerResponse = gson.fromJson(result, RegisterToDoResponse.class);
		assertEquals(registerResponse.getMessage(), "El to do ha sido guardado de manera correcta.");
	}
	
	@Test
	public void itShouldReturnMessageOfTitleUsedWhenTitleIsDB() throws Exception{
		RegisterRequest register = new RegisterRequest();
		register.setTitle("Algo");
		register.setDescription("Una descripcion");
		
		String toDoJson = gson.toJson(register);
		
		String result = mvc.perform(post("/registerToDo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toDoJson))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RegisterToDoResponse registerResponse = gson.fromJson(result, RegisterToDoResponse.class);
		assertEquals(registerResponse.getMessage(), "El titulo que usted quiere usar, ya ha sido registrado anteriormente. Por favor, ingrese un titulo diferente.");
	}
}
