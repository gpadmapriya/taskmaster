package com.padmapg.taskmaster.Controllers;

import com.padmapg.taskmaster.Models.History;
import com.padmapg.taskmaster.Models.Task;
import com.padmapg.taskmaster.Models.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")

public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksForUser(@PathVariable String name) {
        return taskRepository.findByAssignee(name);
    }

    @PostMapping("/tasks")
    public Task addNewTask(@RequestBody Task task) {
        Task t = new Task(task.getId(), task.getTitle(), task.getDescription(), "Available", "none");
        historySetter(t);
        taskRepository.save(t);
        return t;
    }


    @PutMapping("/tasks/{id}/state")
    public Task updateTaskStatus (@PathVariable String id) {
        Task task = taskRepository.findById(id).get();
        if (task.getStatus().equals("Assigned")) {
            task.setStatus("Accepted");
            historySetter(task);
        } else if (task.getStatus().equals("Accepted")) {
            task.setStatus("Finished");
            historySetter(task);
        }
        taskRepository.save(task);
        return task;
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task addTaskAssignee(@PathVariable String id, @PathVariable String assignee) {
        Task t = taskRepository.findById(id).get();
        t.setAssignee(assignee);
        t.setStatus("Assigned");
        historySetter(t);
        taskRepository.save(t);
        return t;
    }

    @DeleteMapping("/tasks/{id}/delete")
    public Task deleteTaskStatus(@PathVariable String id) {
        Task t = taskRepository.findById(id).get();
        taskRepository.delete(t);
        return t;
    }

    //Helper method
    private void historySetter(Task t) {
        History history = new History(new Date().toString(), t.getStatus());
        t.addHistory(history);
    }

}
