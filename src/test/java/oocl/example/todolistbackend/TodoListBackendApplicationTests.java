package oocl.example.todolistbackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import oocl.example.todolistbackend.repository.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoListBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TodoListRepository todoListRepository;

    @BeforeEach
    void setUp() {
        todoListRepository.clear();
    }

    @Test
    void should_create_todo_when_post_given_a_valid_body() throws Exception {

        String requestBody = """
                  {
                       "text": "跑步"
                  }
                """;
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("跑步"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_retrun_422_Unprocessable_when_post_given_a_empty_text() throws Exception {

        String requestBody = """
                  {
                       "text": ""
                  }
                """;
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnprocessableEntity());
    }



    @Test
    void should_return_empty_array_when_findAll_given_no_parameters_and_data_no_exist() throws Exception {
        mockMvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


}
