package org.example.controller;
import org.example.domain.Task;
import org.example.domain.Employee;
import org.example.service.TaskService;
import org.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static java.util.Objects.isNull;

@Controller
public class EmployeeController{
    private final EmployeeService employeeService;
    private final TaskService taskService;
    public EmployeeController(EmployeeService employeeService, TaskService taskService)
    {
        this.employeeService = employeeService;
        this.taskService=taskService;
    }
    @RequestMapping(value = "/employees/", method = RequestMethod.GET)
    public String getAllEmployees(Model model){
        List<Employee> employees= (List<Employee>) employeeService.getAll();
        model.addAttribute("employees", employees);
        List <Task> tasks = (List<Task>) taskService.getAll();
        model.addAttribute("tasks", tasks);
        List <Long> tasksIds = taskService.getTasksIds();
        model.addAttribute("tasksIds", tasksIds);
        return "employees";
    }
    @PostMapping("/employees/{id}")
    public String editEmployee(Model model,
                               @PathVariable Long id,
                               @RequestBody EmployeeInfo employeeInfo){

        if(isNull(id) || id<0) {
            throw new RuntimeException("Invalid id");
        }
        Employee employee= employeeService
                .editEmployee(id, employeeInfo.getName(),
                        employeeInfo.getPosition(),  employeeInfo.getBirthday(),
                        employeeInfo.getGender(),
                        employeeInfo.getTasksIds());
        employee.setEmployeesTasks(employeeInfo.getTaskList());

        return getAllEmployees(model);
    }
    @PostMapping("/employees/")
    public String addEmployee(Model model,
                              @RequestBody EmployeeInfo employeeInfo){

        Employee employee= employeeService.createEmployee(employeeInfo.getId(), employeeInfo.getName(),
                employeeInfo.getPosition(), employeeInfo.getBirthday(), employeeInfo.getGender(),
                employeeInfo.getTasksIds());

        return getAllEmployees(model);
    }
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(Model model,
                                 @PathVariable Long id) {
        if(isNull(id) || id<0) {
            throw new RuntimeException("Invalid id");
        }
        employeeService.deleteEmployee(id);

        return getAllEmployees(model);
    }
}