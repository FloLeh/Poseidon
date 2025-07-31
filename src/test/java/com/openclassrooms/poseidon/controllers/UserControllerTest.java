package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.services.UserService;
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
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user")
    public void userPage_shouldDisplayThePageWithUsers() throws Exception {
        given(userService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", List.of()));

        verify(userService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void userAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void userValidateSubmit_shouldDisplaySaveTheUser() throws Exception {
        given(userService.create(any())).willReturn(1);

        mockMvc.perform(post("/user/validate")
                        .param("username", "testUserName")
                        .param("password", "testPassword")
                        .param("fullname", "testFullName")
                        .param("role", "user")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        verify(userService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void userValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", "")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeHasFieldErrors("user", "fullname"))
                .andExpect(model().attributeHasFieldErrors("user", "role"));
    }

    @Test
    @WithMockUser(username = "user")
    public void userUpdatePage_shouldDisplayTheUser() throws Exception {
        User user = new User();
        user.setId(1);
        given(userService.getById(user.getId())).willReturn(user);

        mockMvc.perform(get("/user/update/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", user));

        verify(userService, times(1)).getById(user.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void userUpdateSubmit_shouldUpdateTheUser() throws Exception {
        User userToUpdate = new User();
        userToUpdate.setId(1);

        User user = new User("testUserName", "testPassword", "testFullName", "user");
        doNothing().when(userService).update(user, userToUpdate.getId());

        mockMvc.perform(post("/user/update/" + userToUpdate.getId())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void userUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        User userToUpdate = new User();
        userToUpdate.setId(1);

        User user = new User("", "", "", "");

        mockMvc.perform(post("/user/update/" + userToUpdate.getId())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user", "username"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeHasFieldErrors("user", "fullname"))
                .andExpect(model().attributeHasFieldErrors("user", "role"));
    }

    @Test
    @WithMockUser(username = "user")
    public void userDelete_shouldDeleteTheUser() throws Exception {
        User userToDelete = new User();
        userToDelete.setId(1);

        doNothing().when(userService).deleteById(userToDelete.getId());

        mockMvc.perform(get("/user/delete/" + userToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(userService, times(1)).deleteById(userToDelete.getId());
    }

}
