package com.wolkabout.exam.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wolkabout.exam.api.dto.TaskCreationDetails;
import com.wolkabout.exam.service.TaskService;
import com.wolkabout.exam.model.Task;



@RestController
@RequestMapping("/api/tasks")
public class TaskApi {
	
	private final TaskService taskService;
	
	@Autowired
	public TaskApi(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping("/user/{userId}")
	public ResponseEntity<Task> addTaskToUser(@PathVariable Long userId, @RequestBody TaskCreationDetails taskCreationDetails) {
		LocalDateTime startTime = taskCreationDetails.getStartTime();
		LocalDateTime endTime = taskCreationDetails.getEndTime();
		String description = taskCreationDetails.getDescription();
		
		Task createdTask = taskService.addTaskToUser(userId, taskCreationDetails);
		
		return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Task>> getAllTasksForUser(@PathVariable Long userId) {
		List<Task> tasks = taskService.GetAllTasksForUser(userId);
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
}
