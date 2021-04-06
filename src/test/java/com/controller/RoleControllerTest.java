package com.controller;

import com.config.ConfigAppTest;
import com.domain.Role;
import com.domain.enums.ROLE;
import com.exception.handler.CustomExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.Validator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This is class for testing {@link RoleController}
 * class and its methods.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigAppTest.class)
@WebAppConfiguration
public class RoleControllerTest {


    /**
     * This is field {@link RoleService}.
     */
    @Mock
    private RoleService roleService;


    /**
     * This is field {@link RoleController}.
     */
    @InjectMocks
    private RoleController roleController;


    /**
     * This is field {@link MockMvc}.
     */
    private MockMvc mockMvc;


    /**
     * This is field {@link Validator} it is used for validation data
     * when test some {@link RoleController} and inject it thought
     * {@link RoleController#setValidator(Validator)}.
     */
    @Autowired
    private Validator validator;


    /**
     * This is method which configure and build {@link MockMvc} object.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
        roleController.setValidator(validator);
    }


    /**
     * This is test method for testing
     * {@link RoleController#saveRole(Role)} method.
     *
     * @throws Exception can throw.
     */
    @Test
    public void shouldSaveRoleWithoutThrowingException() throws Exception {
        Mockito.doNothing()
                .when(roleService)
                .saveRole(ArgumentMatchers.any(Role.class));

        String json = new ObjectMapper()
                .writeValueAsString(new Role(0, ROLE.ROLE_USER));

        mockMvc.perform(post("/role/roles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
