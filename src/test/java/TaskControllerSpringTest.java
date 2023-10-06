import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.PetProjectApplication;
import org.example.controller.TaskInfo;
import org.example.domain.Level;
import org.example.domain.Status;
import org.example.service.EmployeeService;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@ContextConfiguration(classes = PetProjectApplication.class)
public class TaskControllerSpringTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private TaskService taskService;

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("tasks"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("tasks"));
    }
    @Test
   public void testEditTask() throws Exception {
       Long id = 1L;
       Long employeeId = 2L;
       TaskInfo taskInfo = new TaskInfo();
       taskInfo.setId(id);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/edit/{id}/{employeeId}", id, employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("tasks"));
    }

    @Test
    public void testAddTask() throws Exception {
        Long employeeId = 2L;
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setDescription("Do nothing");
        taskInfo.setStatus(Status.DONE);
        taskInfo.setLevel(Level.BURNING);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks/add/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskInfo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("tasks"));
    }
    @Test
    public void testDeleteTask() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("tasks"));
    }
}


