package com.padmapg.taskmaster.Controllers;

import com.padmapg.taskmaster.Models.Task;
import com.padmapg.taskmaster.Models.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")

public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription( task.getDescription() );
        newTask.setStatus("Available");
        taskRepository.save(newTask);
        return newTask;
    }

}
