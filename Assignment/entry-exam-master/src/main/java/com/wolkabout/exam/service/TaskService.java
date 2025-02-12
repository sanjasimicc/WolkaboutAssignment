package com.wolkabout.exam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolkabout.exam.api.dto.TaskCreationDetails;
import com.wolkabout.exam.repository.TaskRepository;
import com.wolkabout.exam.repository.UserRepository;
import com.wolkabout.exam.model.Task;
import com.wolkabout.exam.model.User;


import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Task addTaskToUser(Long userId, TaskCreationDetails taskCreationDetails) {
		 User user = userRepository.findById(userId)
                 .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

         Task task = new Task();
         task.setUser(user);
         task.setStartTime(taskCreationDetails.getStartTime());
         task.setEndTime(taskCreationDetails.getEndTime());
         task.setDescription(taskCreationDetails.getDescription());

         return taskRepository.save(task);
	}
	
	public List<Task> GetAllTasksForUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
		
		return user.getTasks();
	}
}
