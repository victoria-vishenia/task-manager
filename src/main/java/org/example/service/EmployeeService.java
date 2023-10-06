package org.example.service;
import org.example.domain.*;
import org.example.repo.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TaskService taskService;

    public EmployeeService(EmployeeRepository employeeRepository,
                           TaskService taskService) {
        this.employeeRepository = employeeRepository;
        this.taskService=taskService;
    }
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }
    @Transactional
    public Employee createEmployee(Long employeeId, String name, Position position,
                                   LocalDate birthday, Gender gender, List <Long> taskIds) {

        Employee newEmployee = new Employee();

        if (birthday == null) {
            newEmployee.setBirthday(null);
        } else {
            LocalDate localDateNow = LocalDate.now();
            Period ageDifference = birthday.until(localDateNow);
            int years = ageDifference.getYears();
            if (years < 18) {
                throw new RuntimeException("Too young person for this job.");
            } else {
                newEmployee.setId(employeeId);
                newEmployee.setName(name);
                newEmployee.setPosition(position);
                newEmployee.setGender(gender);
                newEmployee.setBirthday(birthday);
            }
        }
        return editTasksForEditedEmployee(taskIds, newEmployee);
    }

    @Transactional
    public Employee editEmployee(Long employeeId, String name, Position position,
                                 LocalDate birthday, Gender gender, List <Long> taskIds) {
        Employee employee = employeeRepository.findById(employeeId).get();

        if (birthday == null) {
            employee.setBirthday(null);
        } else {
            LocalDate localDateNow = LocalDate.now();
            Period ageDifference = birthday.until(localDateNow);
            int years = ageDifference.getYears();

            if (years < 18) {
                throw new RuntimeException("Too young person for this job.You can't hire a child");
            } else {
                employee.setName(name);
                employee.setPosition(position);
                employee.setBirthday(birthday);
                employee.setGender(gender);
            }
        }
        return editTasksForEditedEmployee(taskIds, employee);
    }

    private Employee editTasksForEditedEmployee(List<Long> taskIds, Employee employee) {

        if (taskIds!=null) {
            List<Task> selectedTasks = taskService.getTasksByIds(taskIds);
            List <Task> actualTasks = new ArrayList<>();

            for (Task task : selectedTasks) {
                if(task.getEmployee()== null) {
                    actualTasks.add(task);
                }
            }
            employee.setEmployeesTasks(actualTasks);
            for (Task task : actualTasks) {
                task.setEmployee(employee);
            }
        } else {
            employee.setEmployeesTasks(null);
        }
        return employeeRepository.save(employee);
    }
    @Transactional
    public void deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        Employee employee1 = employee.get();

        if (isNull(employee1)) {
            throw new RuntimeException("Employee not exist");
        } else {
            List <Task> tasks = employee1.getEmployeesTasks();
            for (Task task : tasks){
                task.setEmployee(null);
            }
            employeeRepository.deleteById(id);
        }
    }
}