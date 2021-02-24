package com.controller;

import com.constant.LoggerConstants;
import com.domain.Role;
import com.exception.custom_exception.RoleException;
import com.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * Class {@link RoleController} using to perform role operations kike:
 * saveRole.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@RestController
@RequestMapping("role")
public class RoleController {

    /**
     * This is field of class {@link Logger}
     * returns a logger for this controller.
     */
    private static final Logger LOGGER = Logger.
            getLogger(RoleController.class);

    /**
     * Field {@link RoleService} is object instance
     * of {@link RoleService} interface with {@link Role} type.
     * It connects realization part with user application.
     */
    private final RoleService roleService;

    /**
     * This field use for validation bean instances.
     * This is object instance of {@link Validator}.
     */
    private final Validator validator;

    /**
     * This is a constructor that injects object gain of the
     * {@link RoleService} into the {@link RoleController} class.
     *
     * @param serviceRole   {@link RoleService}.
     * @param validatorRole {@link Validator}.
     */
    @Autowired
    public RoleController(final RoleService serviceRole,
                          final Validator validatorRole) {
        this.roleService = serviceRole;
        this.validator = validatorRole;
    }

    /**
     * This is an class method pass Role to the
     * {@link RoleService#saveRole(Role)}
     * to save roles.
     *
     * @param role Role.
     */
    @PostMapping(value = "/roles")
    public void saveRole(@RequestBody final Role role)
            throws RoleException {
        Set<ConstraintViolation<Role>> violations = validator
                .validate(role);
        if (!violations.isEmpty()) {
            LOGGER.error(violations.toString());
            throw new ConstraintViolationException(violations);
        }
        try {
            roleService.saveRole(role);
            LOGGER.info(LoggerConstants.ROLE_SAVED);
        } catch (Exception e) {
            LOGGER.error(e.getCause().getCause().toString());
            throw new RoleException(e.getCause().getCause().toString());
        }
    }
}
