package com.padmapg.taskmaster.Models;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, String> {
    Optional<Task> findById(String id);
}
