import org.example.PetProjectApplication;
import org.example.domain.Employee;
import org.example.domain.Gender;
import org.example.domain.Position;
import org.example.domain.Task;
import org.example.repo.EmployeeRepository;
import org.example.service.EmployeeService;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PetProjectApplication.class)
public class EmployeeServiceSpringTest {

    @Autowired
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private TaskService taskService;

    @Test
    public void testCreateEmployee() {
        Long employeeId = 1L;
        String name = "John Doe";
        Position position = Position.MANAGER;
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        Gender gender = Gender.MALE;
        List<Long> taskIds = Arrays.asList(1L, 2L);

        Employee newEmployee = new Employee();
        newEmployee.setId(employeeId);
        newEmployee.setName(name);
        newEmployee.setPosition(position);
        newEmployee.setGender(gender);
        newEmployee.setBirthday(birthday);

        when(taskService.getTasksByIds(taskIds)).thenReturn(Arrays.asList(new Task(), new Task()));
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        Employee createdEmployee = employeeService.createEmployee(employeeId, name, position, birthday, gender, taskIds);

        assertNotNull(createdEmployee);
        assertEquals(employeeId, createdEmployee.getId());
        assertEquals(name, createdEmployee.getName());
        assertEquals(position, createdEmployee.getPosition());
        assertEquals(gender, createdEmployee.getGender());
        assertEquals(birthday, createdEmployee.getBirthday());
        assertNotNull(createdEmployee.getEmployeesTasks());

        verify(taskService, times(1)).getTasksByIds(taskIds);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testEditEmployee() {
        Long employeeId = 1L;
        String name = "John Doe";
        Position position = Position.MANAGER;
        LocalDate birthday = LocalDate.of(1980, 1, 1);
        Gender gender = Gender.MALE;
        List<Long> taskIds = Arrays.asList(1L, 2L);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setName("Old Name");
        existingEmployee.setPosition(position);
        existingEmployee.setGender(gender);
        existingEmployee.setBirthday(birthday);

        Task task1 = new Task();
        task1.setId(1L);
        Task task2 = new Task();
        task2.setId(2L);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(taskService.getTasksByIds(taskIds)).thenReturn(Arrays.asList(task1, task2));
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(existingEmployee);

        Employee updatedEmployee = employeeService.editEmployee(employeeId, name, position, birthday, gender, taskIds);

        assertEquals(name, updatedEmployee.getName());
        assertEquals(position, updatedEmployee.getPosition());
        assertEquals(birthday, updatedEmployee.getBirthday());
        assertEquals(gender, updatedEmployee.getGender());

        Mockito.verify(employeeRepository).findById(employeeId);
        Mockito.verify(taskService).getTasksByIds(taskIds);
        Mockito.verify(employeeRepository).save(existingEmployee);
    }
}
