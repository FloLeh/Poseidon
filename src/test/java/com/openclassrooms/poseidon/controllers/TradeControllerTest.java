package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.services.TradeService;
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
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    @Test
    @WithMockUser(username = "user")
    public void bidListPage_shouldDisplayThePageWithBidLists() throws Exception {
        given(tradeService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"))
                .andExpect(model().attribute("trades", List.of()));

        verify(tradeService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void tradeValidateSubmit_shouldDisplaySaveTheBidList() throws Exception {
        given(tradeService.create(any())).willReturn(1);

        mockMvc.perform(post("/trade/validate")
                        .param("account", "testAccount")
                        .param("type", "testType")
                        .param("buyQuantity", "100")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        verify(tradeService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "")
                        .param("type", "")
                        .param("buyQuantity", "-1")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"))
                .andExpect(model().attributeHasFieldErrors("trade", "type"))
                .andExpect(model().attributeHasFieldErrors("trade", "buyQuantity"));
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeUpdatePage_shouldDisplayTheBidList() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(1);
        given(tradeService.getById(trade.getId())).willReturn(trade);

        mockMvc.perform(get("/trade/update/" + trade.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("trade", trade));

        verify(tradeService, times(1)).getById(trade.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeUpdateSubmit_shouldUpdateTheBidList() throws Exception {
        Trade tradeToUpdate = new Trade();
        tradeToUpdate.setTradeId(1);

        Trade trade = new Trade("testAccount", "testType", 100.0);
        doNothing().when(tradeService).update(trade, tradeToUpdate.getId());

        mockMvc.perform(post("/trade/update/" + tradeToUpdate.getId())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        Trade tradeToUpdate = new Trade();
        tradeToUpdate.setTradeId(1);

        Trade trade = new Trade("", "", -1.0);

        mockMvc.perform(post("/trade/update/" + tradeToUpdate.getId())
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("trade", "account"))
                .andExpect(model().attributeHasFieldErrors("trade", "type"))
                .andExpect(model().attributeHasFieldErrors("trade", "buyQuantity"));
    }

    @Test
    @WithMockUser(username = "user")
    public void tradeDelete_shouldDeleteTheBidList() throws Exception {
        Trade tradeToDelete = new Trade();
        tradeToDelete.setTradeId(1);

        doNothing().when(tradeService).deleteById(tradeToDelete.getId());

        mockMvc.perform(get("/trade/delete/" + tradeToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(tradeService, times(1)).deleteById(tradeToDelete.getId());
    }

}
