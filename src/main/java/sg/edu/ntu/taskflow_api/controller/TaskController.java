package sg.edu.ntu.taskflow_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.ntu.taskflow_api.model.Task;
import sg.edu.ntu.taskflow_api.service.TaskService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> allTasks = taskService.findAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(allTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task t = taskService.findTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(t);
    }

    @PostMapping("")
    public ResponseEntity<Task> createNewTask(@RequestBody Task task) {
        Task t = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateExistingTask(@PathVariable Long id, @RequestBody Task task) {
        Task t = taskService.updateTask(id, task);
        return ResponseEntity.status(HttpStatus.OK).body(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build(); // required to be 200 by assignment
    }

    @PutMapping("{id}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long id) {
        Task t = taskService.markTaskAsComplete(id);
        return ResponseEntity.status(HttpStatus.OK).body(t);
    }
}
