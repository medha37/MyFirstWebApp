package com.codeword.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class ToDoService {
	
private static List<ToDo> todos = new ArrayList<>();
	
	private static int todosCount = 0;
	
	static {
		todos.add(new ToDo(++todosCount, "Medha","Get AWS Certified", 
							LocalDate.now().plusYears(1), false ));
		todos.add(new ToDo(++todosCount, "Medha","Learn DevOps", 
				LocalDate.now().plusYears(2), false ));
		todos.add(new ToDo(++todosCount, "Medha","Learn Full Stack Development", 
				LocalDate.now().plusYears(3), false ));
	}
	
	public List<ToDo> findByUsername(String username){
		Predicate<? super ToDo> predicate = todo -> todo.getUserName() == username;
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean status) {
		ToDo todo = new ToDo(++todosCount,username,description,targetDate,status);
		todos.add(todo);
	}
	
	public void deleteById(int id) {
		//todo.getId() == id
		// todo -> todo.getId() == id
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public ToDo findById(int id) {
		Predicate<? super ToDo> predicate = todo -> todo.getId() == id;
		ToDo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid ToDo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}

}
