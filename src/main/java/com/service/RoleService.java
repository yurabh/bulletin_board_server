package com.service;

import com.domain.Role;

/**
 * {@link RoleService} interface binds realization part with user and
 * database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

public interface RoleService {

    /**
     * This method takes an {@link Role} and transmits him to
     * the data base for saving.
     *
     * @param role {@link Role}.
     */
    void saveRole(Role role);
}
