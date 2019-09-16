package com.codewitches.taskmasterbackend.Controllers;

import com.codewitches.taskmasterbackend.Models.HistoryObj;
import com.codewitches.taskmasterbackend.Models.Task;
import com.codewitches.taskmasterbackend.Repository.S3Client;
import com.codewitches.taskmasterbackend.Repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
        return "landingPage";
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task addNewUser (@RequestBody Task task) {
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setStatus("Available");
        newTask.addHistory(new HistoryObj(newTask.getStatus()));
        taskRepository.save(newTask);
        return newTask;
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
        Task taskToUpdate = taskRepository.findById(id).get();
        if (taskToUpdate.getStatus().equals("Available")) {
            taskToUpdate.setStatus("Assigned");
        } else if (taskToUpdate.getStatus().equals("Assigned")) {
            taskToUpdate.setStatus("Accepted");
        } else if (taskToUpdate.getStatus().equals("Accepted")) {
            taskToUpdate.setStatus("Finished");
        } else if (taskToUpdate.getStatus().equals("Finished")) {
            return taskToUpdate;
        }
        taskToUpdate.addHistory(new HistoryObj("--> " + taskToUpdate.getStatus()));
        taskRepository.save(taskToUpdate);
        return taskToUpdate;
    }

    @DeleteMapping("/tasks/{id}")
    public Task deleteTaskStatus(@PathVariable String id) {
        Task taskToDelete = taskRepository.findById(id).get();
        taskRepository.delete(taskToDelete);
        return taskToDelete;
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksByAssignee(@PathVariable String name) {
        return (List) taskRepository.findAllByAssignee(name).get();
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task updateTaskAssignee(@PathVariable String id, @PathVariable String assignee) {
        Task taskToUpdate = taskRepository.findById(id).get();
        taskToUpdate.setAssignee(assignee);
        taskToUpdate.setStatus("Assigned");
        taskToUpdate.addHistory(new HistoryObj("--> Assigned to " + assignee));
        taskRepository.save(taskToUpdate);
        return taskToUpdate;
    }
}
