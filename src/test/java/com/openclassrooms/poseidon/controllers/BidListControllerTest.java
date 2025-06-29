package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.services.BidListService;
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
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BidListService bidListService;

    @Test
    @WithMockUser(username = "user")
    public void bidListPage_shouldDisplayThePageWithBidLists() throws Exception {
        given(bidListService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attribute("bidLists", List.of()));

        verify(bidListService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void bidListValidateSubmit_shouldDisplaySaveTheBidList() throws Exception {
        given(bidListService.create(any())).willReturn(1);

        mockMvc.perform(post("/bidList/validate")
                        .param("account", "testAccount")
                        .param("type", "testType")
                        .param("bidQuantity", "100")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));

        verify(bidListService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("bidQuantity", "-1")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(model().attributeHasFieldErrors("bidList", "type"))
                .andExpect(model().attributeHasFieldErrors("bidList", "bidQuantity"));
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListUpdatePage_shouldDisplayTheBidList() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        given(bidListService.getById(bidList.getId())).willReturn(bidList);

        mockMvc.perform(get("/bidList/update/" + bidList.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidList", bidList));

        verify(bidListService, times(1)).getById(bidList.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListUpdateSubmit_shouldUpdateTheBidList() throws Exception {
        BidList bidListToUpdate = new BidList();
        bidListToUpdate.setBidListId(1);

        BidList bidList = new BidList("testAccount", "testType", 100.0);
        doNothing().when(bidListService).update(bidList, bidListToUpdate.getId());

        mockMvc.perform(post("/bidList/update/" + bidListToUpdate.getId())
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        BidList bidListToUpdate = new BidList();
        bidListToUpdate.setBidListId(1);

        BidList bidList = new BidList("", "", -1.0);

        mockMvc.perform(post("/bidList/update/" + bidListToUpdate.getId())
                        .param("account", bidList.getAccount())
                        .param("type", bidList.getType())
                        .param("bidQuantity", String.valueOf(bidList.getBidQuantity()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(model().attributeHasFieldErrors("bidList", "type"))
                .andExpect(model().attributeHasFieldErrors("bidList", "bidQuantity"));
    }

    @Test
    @WithMockUser(username = "user")
    public void bidListDelete_shouldDeleteTheBidList() throws Exception {
        BidList bidListToDelete = new BidList();
        bidListToDelete.setBidListId(1);

        doNothing().when(bidListService).deleteById(bidListToDelete.getId());

        mockMvc.perform(delete("/bidList/delete/" + bidListToDelete.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(bidListService, times(1)).deleteById(bidListToDelete.getId());
    }

}
