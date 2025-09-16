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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void should_return_one_todo_when_get_given_only_one_exist() throws Exception {
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
        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_return_422_Unprocessable_when_post_given_a_empty_text() throws Exception {

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

    @Test
    void should_update_todo_when_put_given_valid_id_and_body_without_id() throws Exception {
        String requestBody = """
                  {
                       "text": "看书"
                  }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("看书"))
                .andExpect(jsonPath("$.done").value(false));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(contentAsString).get("id").asLong();
        String updateRequestBody = """
                   {
                      "text": "跑步",
                      "done": true
                   }
                """;
        mockMvc.perform(put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("跑步"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_update_todo_use_path_id_when_put_given_valid_id_and_body_with_id() throws Exception {
        String requestBody = """
                  {
                       "text": "看书"
                  }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("看书"))
                .andExpect(jsonPath("$.done").value(false));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(contentAsString).get("id").asLong();
        String updateRequestBody = """
                   {
                      "id": 45,
                      "text": "跑步",
                      "done": true
                   }
                """;
        mockMvc.perform(put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("跑步"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_not_update_todo_when_put_given_not_existed_id() throws Exception {
        String requestBody = """
                  {
                       "text": "看书"
                  }
                """;
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("看书"))
                .andExpect(jsonPath("$.done").value(false));
        String updateRequestBody = """
                   {
                      "text": "跑步",
                      "done": true
                   }
                """;
        mockMvc.perform(put("/todos/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_not_update_todo_when_put_given_a_request_body_without_done() throws Exception {
        String requestBody = """
                  {
                       "text": "看书"
                  }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("看书"))
                .andExpect(jsonPath("$.done").value(false));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(contentAsString).get("id").asLong();
        String updateRequestBody = """
                   {
                        "text": ""
                   }
                """;
        mockMvc.perform(put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_not_update_todo_when_put_given_an_empty_request_body() throws Exception {
        String requestBody = """
                  {
                       "text": "看书"
                  }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("看书"))
                .andExpect(jsonPath("$.done").value(false));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(contentAsString).get("id").asLong();
        String updateRequestBody = "{}";
        mockMvc.perform(put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_delete_todo_when_delete_given_existing_id() throws Exception {
        String requestBody = """
                  {
                       "text": "要删除的任务"
                  }
                """;
        ResultActions resultActions = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("要删除的任务"))
                .andExpect(jsonPath("$.done").value(false));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Long id = objectMapper.readTree(contentAsString).get("id").asLong();
        mockMvc.perform(delete("/todos/{id}", id))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_return_404_when_delete_given_non_existing_id() throws Exception {
        // 尝试删除不存在的 todo
        mockMvc.perform(delete("/todos/{id}", 999))
                .andExpect(status().isNotFound());
    }
}
