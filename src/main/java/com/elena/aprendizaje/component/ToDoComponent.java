package com.elena.aprendizaje.component;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;

@Component
public class ToDoComponent {
	
	public boolean checkAttributes(Object obj) {
		try {
			for(Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if(f.get(obj) == null) {
					return false;
				}
				if(f.getType() == String.class) {
					if(f.get(obj).equals("")) {
						return false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean checkId(Long idToDo) {
		if(idToDo != 0 && idToDo != null) {
			return true;
		}			
		return false;
	}
	
}
