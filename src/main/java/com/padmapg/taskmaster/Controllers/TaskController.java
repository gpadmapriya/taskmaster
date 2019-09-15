package com.padmapg.taskmaster.Controllers;

import com.padmapg.taskmaster.Models.History;
import com.padmapg.taskmaster.Models.Task;
import com.padmapg.taskmaster.Models.TaskRepository;
import com.padmapg.taskmaster.config.S3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")

public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }
    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getSingleTask(@PathVariable String id) {
        return taskRepository.findById(id).get();
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksForUser(@PathVariable String name) {
        return taskRepository.findByAssignee(name);
    }

    @PostMapping("/tasks")
    public Task addNewTask(@RequestBody Task task) {
        Task t = new Task(task.getId(), task.getTitle(), task.getDescription(), "Available", "none");
        t.addHistory();
        taskRepository.save(t);
        return t;
    }

    @PostMapping("/tasks/{id}/image")
    public Task addImageToTask(@PathVariable String id, @RequestPart(value = "file") MultipartFile file) {
        String pic = this.s3Client.uploadFile(file);
        Task t = taskRepository.findById(id).get();
        t.setImage(pic);
        taskRepository.save(t);
        return t;
    }

    @PutMapping("/tasks/{id}/state")
    public Task updateTaskStatus (@PathVariable String id) {
        Task task = taskRepository.findById(id).get();
        if (task.getStatus().equals("Assigned")) {
            task.setStatus("Accepted");
            task.addHistory();
        } else if (task.getStatus().equals("Accepted")) {
            task.setStatus("Finished");
            task.addHistory();
        }
        taskRepository.save(task);
        return task;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task addTaskAssignee(@PathVariable String id, @PathVariable String assignee) {
        Task t = taskRepository.findById(id).get();
        t.setAssignee(assignee);
        t.setStatus("Assigned");
        t.addHistory();
        taskRepository.save(t);
        return t;
    }

    @DeleteMapping("/tasks/{id}")
    public Task deleteTaskStatus(@PathVariable String id) {
        Task t = taskRepository.findById(id).get();
        taskRepository.delete(t);
        return t;
    }

}
