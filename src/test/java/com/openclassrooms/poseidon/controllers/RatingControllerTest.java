package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.services.RatingService;
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
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RatingService ratingService;

    @Test
    @WithMockUser(username = "user")
    public void ratingPage_shouldDisplayThePageWithRatings() throws Exception {
        given(ratingService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", List.of()));

        verify(ratingService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void ratingValidateSubmit_shouldDisplaySaveTheRating() throws Exception {
        given(ratingService.create(any())).willReturn(1);

        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "testMoodysRating")
                        .param("sandPRating", "testSandPRating")
                        .param("fitchRating", "testFitchRating")
                        .param("order", "100")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        verify(ratingService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "")
                        .param("sandPRating", "")
                        .param("fitchRating", "")
                        .param("order", "-1")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "sandPRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "fitchRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "order"));
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingUpdatePage_shouldDisplayTheRating() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        given(ratingService.getById(rating.getId())).willReturn(rating);

        mockMvc.perform(get("/rating/update/" + rating.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("rating", rating));

        verify(ratingService, times(1)).getById(rating.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingUpdateSubmit_shouldUpdateTheRating() throws Exception {
        Rating ratingToUpdate = new Rating();
        ratingToUpdate.setId(1);

        Rating rating = new Rating("testMoodysRating", "testSandPRating", "testFitchRating", 100);
        doNothing().when(ratingService).update(rating, ratingToUpdate.getId());

        mockMvc.perform(post("/rating/update/" + ratingToUpdate.getId())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("order", String.valueOf(rating.getOrder()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        Rating ratingToUpdate = new Rating();
        ratingToUpdate.setId(1);

        Rating rating = new Rating("", "", "", -1);

        mockMvc.perform(post("/rating/update/" + ratingToUpdate.getId())
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("order", String.valueOf(rating.getOrder()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "sandPRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "fitchRating"))
                .andExpect(model().attributeHasFieldErrors("rating", "order"));
    }

    @Test
    @WithMockUser(username = "user")
    public void ratingDelete_shouldDeleteTheRating() throws Exception {
        Rating ratingToDelete = new Rating();
        ratingToDelete.setId(1);

        doNothing().when(ratingService).deleteById(ratingToDelete.getId());

        mockMvc.perform(get("/rating/delete/" + ratingToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(ratingService, times(1)).deleteById(ratingToDelete.getId());
    }

}
