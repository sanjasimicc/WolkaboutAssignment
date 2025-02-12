package com.wolkabout.exam.repository;

import com.wolkabout.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContaining(String searchParameter);
}
