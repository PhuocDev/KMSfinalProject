package com.kms.seft203.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.Seft203Application;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.dashboard.widget.Widget;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(

        classes = Seft203Application.class)
@AutoConfigureMockMvc
class DashboardApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    DashboardRepository dashboardRepository;
    private String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }


    @Test
    void getAllShouldReturnOk() throws Exception {
        this.mockMvc.perform(
                        get("/dashboards")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateShouldReturnNewTitleAndLayout() throws Exception {
        Dashboard oldDashboard = dashboardRepository.findAll().get(1);
        Dashboard newDashboard = new Dashboard(oldDashboard.getUserId(), "title just updated", "layout new", oldDashboard.getWidgets());
        this.mockMvc.perform(
                        put("/dashboards/"+oldDashboard.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(toJSONString(newDashboard)))
                .andDo(print())
                .andExpect(jsonPath("$.title").value(newDashboard.getTitle()))
                .andExpect(jsonPath("$.layoutType").value(newDashboard.getLayoutType()));
    }
    @Test
    void addNewDashboardShouldReturnOK() throws Exception {
        Dashboard oldDashboard = dashboardRepository.findAll().get(1);
        Widget widget = new Widget("123345","123","423", 123,456,null);
        List<Widget> list = new ArrayList<>(); list.add(widget);
        Dashboard newDash = new Dashboard(oldDashboard.getUserId(), "title just updated", "layout new", list);
        this.mockMvc.perform(
                post("/dashboards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(toJSONString(newDash)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}