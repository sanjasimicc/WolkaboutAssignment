package com.wolkabout.exam.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(name = "UQ_USER_EMAIL", columnNames = {"email"}))
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;
    
    @OneToMany
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("email='" + getEmail() + "'")
                .add("password='" + getPassword() + "'")
                .add("tasks='" + getTasks() + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getTasks(), user.getTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getTasks());
    }
}
