import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.PetProjectApplication;
import org.example.controller.EmployeeInfo;
import org.example.domain.*;
import org.example.service.EmployeeService;
import org.example.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = PetProjectApplication.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private TaskService taskService;

    @Test
    public void testEditEmployee() throws Exception {
            Long id = 1L;

            EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setId(id);
            employeeInfo.setName("Mars");

            Employee employee= new Employee();
            employee.setId(id);
            employee.setName("Rob");

            mockMvc.perform(MockMvcRequestBuilders.post("/employees/edit/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(employeeInfo)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("employees"));
        }

    @Test
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attributeExists("tasksIds"));
    }

        @Test
    public void testAddEmployee() throws Exception {
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setId(1L);
        employeeInfo.setName("Mark");
        employeeInfo.setPosition(Position.MANAGER);
        employeeInfo.setGender(Gender.MALE);
        employeeInfo.setTasksIds(Arrays.asList(1L, 2L, 3L));

        mockMvc.perform(post("/employees/add/")
                        .content(new ObjectMapper().writeValueAsString(employeeInfo))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("employees"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("employees"));
    }
}


