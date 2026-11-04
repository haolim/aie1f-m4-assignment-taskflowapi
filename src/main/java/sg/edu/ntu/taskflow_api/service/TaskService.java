package sg.edu.ntu.taskflow_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.taskflow_api.exceptions.TaskNotFoundException;
import sg.edu.ntu.taskflow_api.model.Task;
import sg.edu.ntu.taskflow_api.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task findTaskById(Long id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        task.setId(null);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Task taskToUpdate = taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskToUpdate.setIsCompleted(task.getIsCompleted());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setTitle(task.getTitle());
        return taskRepository.save(taskToUpdate);
    }

    public void deleteTask(Long id) {
        taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteTaskById(id);
    }

    public Task markTaskAsComplete(Long id) {
        Task taskToMarkAsCompleted = taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskToMarkAsCompleted.setIsCompleted(true);
        return taskRepository.save(taskToMarkAsCompleted);
    }

}
