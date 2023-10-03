package org.example.service;
import org.example.domain.Employee;
import org.example.domain.Status;
import org.example.repo.EmployeeRepository;
import org.example.repo.TaskRepository;
import org.example.domain.Level;
import org.example.domain.Task;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@EnableTransactionManagement
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    public List <Task> getTasksByIds (List <Long> ids) {
        if (ids == null) {
            return null;
        } else {
            return (List<Task>) taskRepository.findAllById(ids);
        }
    }

    public List <Long> getTasksIds(){
        Iterable<Task> tasks= taskRepository.findAll();
        List <Long> tasksIds = new ArrayList<>();
        if(tasks==null){
            return null;
        } else {
            for (Task task: tasks) {
                tasksIds.add(task.getId());
            }
            return tasksIds;
        }
    }
    @Transactional
    public Task createTask(Long employeeId, Long taskId, String description,
                           Status status, Level level,
                           LocalDate deadline) {

        Task newTask = new Task();

        LocalDate localDateNow = LocalDate.now();
        if (deadline.isBefore(localDateNow)) {
            throw new RuntimeException("Impossible finish task in the past");
        } else {
            newTask.setId(taskId);
            newTask.setDescription(description);
            newTask.setStatus(status);
            newTask.setLevel(level);
            newTask.setDeadline(deadline);

            if (employeeId == null) {
                newTask.setEmployee(null);
            } else if (employeeRepository.existsById(employeeId)) {
                Employee employee = employeeRepository.findById(employeeId).get();
                newTask.setEmployee(employee);
                employee.addTask(newTask);
            }

            taskRepository.save(newTask);
            return newTask;
        }
    }

    @Transactional
    public Task editTask(Long employeeId, Long taskId, String description,
                         Status status, Level level,
                         LocalDate deadline) {
        Task task = null;
        try {
            task = taskRepository.findById(taskId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }

        task.setDescription(description);
        task.setStatus(status);
        task.setLevel(level);
        task.setDeadline(deadline);

        if (employeeId==0) {
            task.setEmployee(null);
        } else if (employeeRepository.existsById(employeeId)) {
            Employee employee = employeeRepository.findById(employeeId).get();
            task.setEmployee(employee);
            employee.addTask(task);
        }
        return taskRepository.save(task);
    }
    @Transactional
    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new RuntimeException("Task not exist");
        } else {
            taskRepository.deleteById(id);
        }
    }
}
