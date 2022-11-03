package com.kms.seft203.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.Seft203Application;
import com.kms.seft203.task.Task;
import com.kms.seft203.task.TaskApi;
import com.kms.seft203.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Seft203Application.class)
@AutoConfigureMockMvc
public class TaskApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void addNewTask_shouldReturnCorrecNewTaskName() throws Exception {
        Task task = new Task("Task just insert", false, "4028ec36841e18e601841e191a300000");

        this.mockMvc.perform(
                    post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(toJSONString(task)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value(task.getTask()))
                .andExpect(jsonPath("$.isCompleted").value(task.getIsCompleted()));
    }

    private String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }

    @Test
    void getAll_shouldReturn200() throws Exception{
        this.mockMvc.perform(
                get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception{
        Task task = taskRepository.findAll().get(1);
        this.mockMvc.perform(
                get("/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.task").value(task.getTask()));
    }

    @Test
    void insertNewTask() throws Exception {
        Task task = new Task("Task just insert", false, "4028ec36841e18e601841e191a300000");
        this.mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(task)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value(task.getTask()))
                .andExpect(jsonPath("$.isCompleted").value(task.getIsCompleted()));
    }

    @Test
    void updateNameofTask_shouldReturnCorrectNewName() throws Exception {
        Task oldTask = taskRepository.findAll().get(1);
        Task task_just_updated_name = new Task("Task just updated", oldTask.getIsCompleted(), oldTask.getUserId());
        this.mockMvc.perform(
                        put("/tasks/"+oldTask.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(task_just_updated_name)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.task").value(task_just_updated_name.getTask()));
    }

    @Test
    void deleteWithAvailableID_shouldReturnAccepted() throws Exception {
        Task deleteTask = taskRepository.findAll().get(1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/tasks/"+deleteTask.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

}
