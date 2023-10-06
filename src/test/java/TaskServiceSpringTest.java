import org.example.PetProjectApplication;
import org.example.domain.Employee;
import org.example.domain.Level;
import org.example.domain.Status;
import org.example.domain.Task;
import org.example.repo.EmployeeRepository;
import org.example.repo.TaskRepository;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PetProjectApplication.class)
public class TaskServiceSpringTest {

    @Autowired
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testCreateTask() {
        Long employeeId = 1L;
        Long taskId = 1L;
        String description = "Test Task";
        Status status = Status.PAUSED;
        Level level = Level.OPTIONAL;
        LocalDate deadline = LocalDate.now().plusDays(7);

        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Task createdTask = taskService.createTask(employeeId, taskId, description, status, level, deadline);

        assertNotNull(createdTask);
        assertEquals(taskId, createdTask.getId());
        assertEquals(description, createdTask.getDescription());
        assertEquals(status, createdTask.getStatus());
        assertEquals(level, createdTask.getLevel());
        assertEquals(deadline, createdTask.getDeadline());
        assertEquals(employee, createdTask.getEmployee());

        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(taskRepository, times(1)).save(createdTask);
    }

    @Test
    public void testEditTask() {
        Long taskId = 1L;
        Long employeeId = 2L;
        String description = "Updated Description";
        Status status = Status.IN_PROGRESS;
        Level level = Level.URGENTLY;
        LocalDate deadline = LocalDate.now().plusDays(7);

        Employee employee = new Employee();
        employee.setId(employeeId);

        Task createdTask= taskService.createTask(employeeId, taskId, "Old Description", Status.DONE
                , Level.BURNING, LocalDate.now().plusDays(3));

        assertNotNull(createdTask);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(createdTask));
        when(employeeRepository.existsById(employeeId)).thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

         taskService.editTask(employeeId, taskId, description, status, level, deadline);

        verify(taskRepository, times(1)).findById(taskId);
        verify(employeeRepository, times(2)).existsById(employeeId);
        verify(employeeRepository, times(1)).findById(employeeId);

        assertAll(
                () -> assertEquals(taskId, createdTask.getId()),
                () -> assertEquals(description, createdTask.getDescription()),
                () -> assertEquals(status, createdTask.getStatus()),
                () -> assertEquals(level, createdTask.getLevel()),
                () -> assertEquals(deadline, createdTask.getDeadline()),
                () -> assertEquals(employee, createdTask.getEmployee())
        );
    }
}

