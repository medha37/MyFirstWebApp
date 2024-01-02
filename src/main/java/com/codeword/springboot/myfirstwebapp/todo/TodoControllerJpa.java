package com.codeword.springboot.myfirstwebapp.todo;

import java.time.LocalDate;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	//private ToDoService todoService;
	
	private TodoRepository todoRepository;
		
	
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username = getLoggedInUsername(model);
		List<ToDo> todos = todoRepository.findByUsername(username);
		model.addAttribute("todos", todos);
		
		return "listTodos";
	}

	
	//GET, POST
		@RequestMapping(value="add-todo", method = RequestMethod.GET)
		public String showNewTodoPage(ModelMap model) {
			String username = getLoggedInUsername(model);
			ToDo todo = new ToDo(0, username, "", LocalDate.now().plusYears(1), false);
			model.put("todo", todo);
			return "todo";
		}

		@RequestMapping(value="add-todo", method = RequestMethod.POST)
		public String addNewTodo(ModelMap model,  @ModelAttribute("todo") @Valid ToDo todo, BindingResult result) {
			
			if(result.hasErrors()) {
				return "todo";
			}
			
			String username = getLoggedInUsername(model);
			todo.setUserName(username);
			todoRepository.save(todo);
			
//			todoService.addTodo(username, todo.getDescription(), 
//					todo.getTargetDate(), false);
			
			return "redirect:list-todos";
		}

	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		//Delete todo
		todoRepository.deleteById(id);
		//todoService.deleteById(id);
		return "redirect:list-todos";
		
	}

	@RequestMapping(value="update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		
		ToDo todo = todoRepository.findById(id).get();
		//ToDo todo = todoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
	}

	@RequestMapping(value="update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid ToDo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}
		
		String username = getLoggedInUsername(model);
		todo.setUserName(username);
		todoRepository.save(todo);
		//todoService.updateTodo(todo);
		return "redirect:list-todos";
	 }

	
		private String getLoggedInUsername(ModelMap model) {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			return authentication.getName();
		}

}
