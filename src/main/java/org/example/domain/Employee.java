package org.example.domain;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
@Entity
@Table(schema = "task_list", name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private Position position;
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @OneToMany(mappedBy = "employee", cascade =  CascadeType.DETACH, fetch = FetchType.EAGER)
    private List<Task> employeesTasks = new ArrayList<>();

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

    public List<Task> getEmployeesTasks() {

        return employeesTasks;
    }

    public void setEmployeesTasks(List<Task> employeesTasks) {

        this.employeesTasks = employeesTasks;
    }

    public void addTask(Task task) {
        task.setEmployee(this);
        employeesTasks.add(task);
    }
}
