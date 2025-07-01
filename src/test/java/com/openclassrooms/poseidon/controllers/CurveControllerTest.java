package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.services.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CurvePointService curvePointService;

    @Test
    @WithMockUser(username = "user")
    public void curvePointPage_shouldDisplayThePageWithCurvePoints() throws Exception {
        given(curvePointService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attribute("curvePoints", List.of()));

        verify(curvePointService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void curvePointValidateSubmit_shouldDisplaySaveTheCurvePoint() throws Exception {
        given(curvePointService.create(any())).willReturn(1);

        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "1")
                        .param("term", "2")
                        .param("value", "3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        verify(curvePointService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "-1")
                        .param("term", "-2")
                        .param("value", "-3")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "value"));
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointUpdatePage_shouldDisplayTheCurvePoint() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        given(curvePointService.getById(curvePoint.getId())).willReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("curvePoint", curvePoint));

        verify(curvePointService, times(1)).getById(curvePoint.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointUpdateSubmit_shouldUpdateTheCurvePoint() throws Exception {
        CurvePoint curvePointToUpdate = new CurvePoint();
        curvePointToUpdate.setId(1);

        CurvePoint curvePoint = new CurvePoint(1, 2.0, 100.0);
        doNothing().when(curvePointService).update(curvePoint, curvePointToUpdate.getId());

        mockMvc.perform(post("/curvePoint/update/" + curvePointToUpdate.getId())
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        CurvePoint curvePointToUpdate = new CurvePoint();
        curvePointToUpdate.setId(1);

        CurvePoint curvePoint = new CurvePoint(-1, -2.0, -1.0);

        mockMvc.perform(post("/curvePoint/update/" + curvePointToUpdate.getId())
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "value"));
    }

    @Test
    @WithMockUser(username = "user")
    public void curvePointDelete_shouldDeleteTheCurvePoint() throws Exception {
        CurvePoint curvePointToDelete = new CurvePoint();
        curvePointToDelete.setId(1);

        doNothing().when(curvePointService).deleteById(curvePointToDelete.getId());

        mockMvc.perform(get("/curvePoint/delete/" + curvePointToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(curvePointService, times(1)).deleteById(curvePointToDelete.getId());
    }

}
