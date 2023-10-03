package org.example.controller;
import org.example.domain.Employee;
import org.example.domain.Level;
import org.example.domain.Status;

import java.time.LocalDate;

public class TaskInfo {
    private Long id;
    private String description;
    private LocalDate deadline;
    private Status status;
    private Level level;
    private Employee employee;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employeeId) {
        this.employee = employee;
    }
}
