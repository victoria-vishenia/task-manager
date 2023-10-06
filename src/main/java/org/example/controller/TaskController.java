package org.example.controller;
import org.example.domain.Employee;
import org.example.domain.Task;
import org.example.service.EmployeeService;
import org.example.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final EmployeeService employeeService;
    public TaskController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService=employeeService;
    }
    @RequestMapping(value = "/tasks/", method = RequestMethod.GET)
    public String getAllTasks(Model model){
        List<Task> tasks= (List<Task>) taskService.getAll();
        model.addAttribute("tasks", tasks);
        List <Employee> employees = (List<Employee>) employeeService.getAll();
        model.addAttribute("employees", employees);
        return "tasks";
    }
    @PostMapping("/tasks/edit/{id}/{employeeId}")
    public String editTask(Model model,
                           @PathVariable Long id,
                           @PathVariable Long employeeId,
                           @RequestBody TaskInfo taskInfo){
        if(isNull(id) || id<0) {
            throw new RuntimeException("Invalid id");
        }

         taskService.editTask(employeeId, id, taskInfo.getDescription(),
                        taskInfo.getStatus(), taskInfo.getLevel(),
                        taskInfo.getDeadline());

        return getAllTasks(model);
    }
    @PostMapping("/tasks/add/{employeeId}")
    public String addTask(Model model,
                          @PathVariable Long employeeId,
                          @RequestBody TaskInfo taskInfo){

        taskService.createTask(employeeId, taskInfo.getId(), taskInfo.getDescription(),
                taskInfo.getStatus(), taskInfo.getLevel(), taskInfo.getDeadline());

        return getAllTasks(model);
    }
    @DeleteMapping("/tasks/{id}")
    public String deleteTask(Model model,
                             @PathVariable Long id) {
        if(isNull(id) || id<0) {
            throw new RuntimeException("Invalid id");
        }
        taskService.deleteTask(id);
        return getAllTasks(model);
    }
}
