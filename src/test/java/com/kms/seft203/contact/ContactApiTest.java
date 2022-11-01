package com.kms.seft203.contact;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.Seft203Application;
import com.kms.seft203.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(

        classes = Seft203Application.class)
@AutoConfigureMockMvc
class ContactApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactRepository contactRepository;


    private String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }

    @Test
    void getAllContact_shouldReturn200() throws Exception{
        this.mockMvc.perform(
                        get("/contacts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void getOne() throws Exception{
        Contact contact = contactRepository.findAll().get(1);
        this.mockMvc.perform(
                        get("/contacts/" + contact.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id").value(contact.getId()))
                .andExpect(jsonPath("$.department").value(contact.getDepartment()));
    }

    @Test
    void insertNewTask() throws Exception {
        Contact contact = new Contact( "Firstname", "Last name", "title", "department just insert", "project", "avatar",28392);
        this.mockMvc.perform(
                        post("/contacts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(contact)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(contact.getTitle()))
                .andExpect(jsonPath("$.department").value(contact.getDepartment()));
    }

    @Test
    void updateNameofTask_shouldReturnCorrectNewName() throws Exception {
        Contact oldContact = contactRepository.findAll().get(1);
        Contact newContact = new Contact( "Firstname v2", "Last name v2", "title updated", "department just updated", oldContact.getProject(),
                oldContact.getAvatar(),oldContact.getEmployeeId());
        this.mockMvc.perform(
                        put("/contacts/"+oldContact.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(newContact)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(newContact.getTitle()));
    }
//
    @Test
    void deleteWithAvailableID_shouldReturnAccepted() throws Exception {
        Contact deleteContact = contactRepository.findAll().get(1);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/contacts/"+deleteContact.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}