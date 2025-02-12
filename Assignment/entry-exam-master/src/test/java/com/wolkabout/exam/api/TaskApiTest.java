package com.wolkabout.exam.api;

import com.wolkabout.exam.BaseTest;
import com.wolkabout.exam.api.dto.TaskCreationDetails;
import com.wolkabout.exam.repository.UserRepository;
import com.wolkabout.exam.model.Task;
import com.wolkabout.exam.model.User;
import com.wolkabout.exam.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class TaskApiTest extends BaseTest {

    private final UserRepository userRepository;
    private final TaskService taskService;

    @Autowired
    public TaskApiTest(UserRepository userRepository, TaskService taskService) {
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    /**
     * REST API test for {@link TaskApi#addTaskToUser(Long, TaskCreationDetails)}
     */
    @Test
    public void addTaskToUserTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        final TaskCreationDetails taskDetails = new TaskCreationDetails();
        taskDetails.setDescription("Sample Task");
        taskDetails.setStartTime(LocalDateTime.now());
        taskDetails.setEndTime(LocalDateTime.now().plusHours(1));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(taskDetails)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is(taskDetails.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").isNotEmpty());
    }

    /**
     * REST API test for {@link TaskApi#getAllTasksForUser(Long)}
     */
    @Test
    public void getAllTasksForUserTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        IntStream.range(0, 5).forEach(index -> {
            final TaskCreationDetails taskDetails = new TaskCreationDetails();
            taskDetails.setDescription("Sample Task " + index);
            taskDetails.setStartTime(LocalDateTime.now());
            taskDetails.setEndTime(LocalDateTime.now().plusHours(1));
            taskService.addTaskToUser(user.getId(), taskDetails);
        });

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/user/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", is("Sample Task 0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", is("Sample Task 1")));
    }
}
