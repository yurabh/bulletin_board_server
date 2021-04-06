package com.service;

import com.config.ConfigAppTest;
import com.domain.Role;
import com.domain.enums.ROLE;
import com.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

/**
 * This is a class for testing the class of the
 * {@link RoleService} and its method.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(ConfigAppTest.class)
@WebAppConfiguration
@Sql(scripts = {
        "classpath:scripts/truncate_tables/truncate_table_role.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RoleServiceTest {


    /**
     * This is a field for injection {@link RoleService} in this class.
     */
    @Autowired
    private RoleService roleService;


    /**
     * This is field for injection {@link RoleRepository} in this class.
     */
    @Autowired
    private RoleRepository roleRepository;


    /**
     * This is a method which test and which storage of
     * {@link RoleService#saveRole(Role)} in the database.
     */
    @Test
    public void saveValidRoleTest() {
        Role role = new Role(0, ROLE.ROLE_ADMIN);
        roleService.saveRole(role);
        role.setId(1);
        final Optional<Role> roleOptional = roleRepository.findById(1);
        roleOptional.ifPresent(role1 -> Assert.assertEquals(role, role1));
    }


    /**
     * This is a method which test and which storage of
     * {@link RoleService#saveRole(Role)} in the database.
     */
    @Test
    public void saveNotValidRoleTest() {
        Role role = new Role(0, ROLE.ROLE_USER);
        roleService.saveRole(role);
        role.setId(1);
        role.setRoleAccount(ROLE.ROLE_ADMIN);
        final Optional<Role> roleOptional = roleRepository.findById(1);
        roleOptional.ifPresent(role1 -> Assert.assertNotEquals(role, role1));
    }
}
