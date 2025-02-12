package com.wolkabout.exam.api;

import com.wolkabout.exam.BaseTest;
import com.wolkabout.exam.api.dto.UserCreationDetails;
import com.wolkabout.exam.api.dto.UserUpdateDetails;
import com.wolkabout.exam.repository.UserRepository;
import com.wolkabout.exam.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;

/**
 * REST API tests for {@link UserApi}
 */
public class UserApiTest extends BaseTest {

    private final UserRepository userRepository;

    @Autowired
    public UserApiTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * REST API test for {@link UserApi#getUser(long)}
     */
    @Test
    public void getUserTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(user.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    /**
     * REST API test for {@link UserApi#listUsers(String)}
     */
    @Test
    public void listUserTest() throws Exception {
        IntStream.range(0, 10).forEach(index -> {
            final User user = new User();
            user.setEmail("user" + index + "@wolkabout.com");
            user.setPassword("wolkabout");
            userRepository.save(user);
        });

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users").param("searchParameters", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("wolkabout"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("user2@wolkabout.com"));
    }

    /**
     * REST API test for {@link UserApi#createUser(UserCreationDetails)}
     */
    @Test
    public void createUserTest() throws Exception {
        final UserCreationDetails details = new UserCreationDetails();
        details.setEmail("user@wolkabout.com");
        details.setPassword("wolkabout");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON).content(toJson(details)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(details.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(details.getEmail()));
    }

    /**
     * REST API test for {@link UserApi#createUser(UserCreationDetails)}
     */
    @Test
    public void createUserDuplicateEmailTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        final UserCreationDetails details = new UserCreationDetails();
        details.setEmail("user@wolkabout.com");
        details.setPassword("wolkabout");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON).content(toJson(details)))
                .andExpect(MockMvcResultMatchers.status().is(409));
    }

    /**
     * REST API test for {@link UserApi#updateUser(long, UserUpdateDetails)}
     */
    @Test
    public void updateUserTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        final UserUpdateDetails details = new UserUpdateDetails();
        details.setPassword("somethingElse");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON).content(toJson(details)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(details.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    /**
     * REST API test for {@link UserApi#createUser(UserCreationDetails)}
     */
    @Test
    public void deleteUserTest() throws Exception {
        final User user = new User();
        user.setEmail("user@wolkabout.com");
        user.setPassword("wolkabout");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * REST API test for {@link UserApi#createUser(UserCreationDetails)}
     */
    @Test
    public void deleteUnknownUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
