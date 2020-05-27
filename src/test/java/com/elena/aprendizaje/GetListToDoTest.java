package com.elena.aprendizaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elena.aprendizaje.responses.GetListToDoResponse;
import com.google.gson.Gson;

@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class GetListToDoTest {
	
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
	public void itShouldReturnMessageOfEnptyFieldsWhenId_to_doIsEnptyOrNotExist() throws Exception{
		String result = mvc.perform(get("/getListToDo/{idToDo}", "0"))
				.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
		
		GetListToDoResponse response = gson.fromJson(result, GetListToDoResponse.class);
		assertEquals("El toDo que usted solicita, no existe en nuestra base de datos.", response.getMessage());
	}
	
	@Test
	public void itShouldReturnMessageOfAllFineWhenId_to_doIsInDb() throws Exception{
		String result = mvc.perform(get("/getListToDo/{idToDo}", "1"))
				.andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
		
		GetListToDoResponse response = gson.fromJson(result, GetListToDoResponse.class);
		assertEquals("La lista fue devuelta de manera correcta.", response.getMessage());
	}
}
