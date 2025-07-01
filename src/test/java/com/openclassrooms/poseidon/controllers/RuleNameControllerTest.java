package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.services.RuleNameService;
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
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RuleNameService ruleNameService;

    @Test
    @WithMockUser(username = "user")
    public void ruleNamePage_shouldDisplayThePageWithRuleNames() throws Exception {
        given(ruleNameService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(model().attribute("ruleNames", List.of()));

        verify(ruleNameService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameAddPage_shouldDisplayThePage() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void ruleNameValidateSubmit_shouldDisplaySaveTheRuleName() throws Exception {
        given(ruleNameService.create(any())).willReturn(1);

        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "testName")
                        .param("description", "testDescription")
                        .param("json", "testJson")
                        .param("template", "testTemplate")
                        .param("sql", "testSqlStr")
                        .param("sqlPart", "testSqlPart")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        verify(ruleNameService, times(1)).create(any());
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameValidateSubmit_withErrors_shouldDisplayErrors() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sql", "")
                        .param("sqlPart", "")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "description"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "json"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "template"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "sql"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "sqlPart"));
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameUpdatePage_shouldDisplayTheRuleName() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);
        given(ruleNameService.getById(ruleName.getId())).willReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ruleName", ruleName));

        verify(ruleNameService, times(1)).getById(ruleName.getId());
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameUpdateSubmit_shouldUpdateTheRuleName() throws Exception {
        RuleName ruleNameToUpdate = new RuleName();
        ruleNameToUpdate.setId(1);

        RuleName ruleName = new RuleName("testName", "testDescription", "testJson", "testTemplate", "testSqlStr", "testSqlPart");
        doNothing().when(ruleNameService).update(ruleName, ruleNameToUpdate.getId());

        mockMvc.perform(post("/ruleName/update/" + ruleNameToUpdate.getId())
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sql", ruleName.getSql())
                        .param("sqlPart", ruleName.getSqlPart())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameUpdateSubmit_WithErrors_shouldDisplayErrors() throws Exception {
        RuleName ruleNameToUpdate = new RuleName();
        ruleNameToUpdate.setId(1);

        RuleName ruleName = new RuleName("", "", "", "", "", "");

        mockMvc.perform(post("/ruleName/update/" + ruleNameToUpdate.getId())
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sql", ruleName.getSql())
                        .param("sqlPart", ruleName.getSqlPart())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("ruleName", "name"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "description"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "json"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "template"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "sql"))
                .andExpect(model().attributeHasFieldErrors("ruleName", "sqlPart"));
    }

    @Test
    @WithMockUser(username = "user")
    public void ruleNameDelete_shouldDeleteTheRuleName() throws Exception {
        RuleName ruleNameToDelete = new RuleName();
        ruleNameToDelete.setId(1);

        doNothing().when(ruleNameService).deleteById(ruleNameToDelete.getId());

        mockMvc.perform(get("/ruleName/delete/" + ruleNameToDelete.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());

        verify(ruleNameService, times(1)).deleteById(ruleNameToDelete.getId());
    }

}
