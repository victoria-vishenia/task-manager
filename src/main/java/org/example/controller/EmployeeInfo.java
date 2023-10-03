package org.example.controller;
import org.example.domain.Position;
import org.example.domain.Task;
import org.example.domain.Gender;

import java.time.LocalDate;
import java.util.List;
public class EmployeeInfo {
    private Long id;
    private String name;
    private Position position;
    private LocalDate birthday;
    private Gender gender;
    private List<Task> taskList;
    private List <Long> tasksIds;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public List<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
    public List<Long> getTasksIds() {
        return tasksIds;
    }
    public void setTasksIds(List<Long> tasksIds) {
        this.tasksIds = tasksIds;
    }
}

