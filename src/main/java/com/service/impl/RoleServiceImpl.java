package com.service.impl;

import com.domain.Role;
import com.repository.RoleRepository;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class {@link RoleServiceImpl} using to perform role operations and
 * save roles in a database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Service
public class RoleServiceImpl implements RoleService {


    /**
     * This is field {@link RoleRepository} for access
     * to role repository.
     */
    private final RoleRepository roleRepository;


    /**
     * This is a constructor with parameter {@link RoleServiceImpl}
     * that injects object gain of the {@link RoleRepository}.
     *
     * @param repositoryRole {@link RoleRepository}.
     */
    @Autowired
    public RoleServiceImpl(final RoleRepository repositoryRole) {
        this.roleRepository = repositoryRole;
    }


    /**
     * This method takes the {@link Role} and transmits it
     * to the {@link RoleRepository#save(Object)} to save the role.
     *
     * @param role {@link Role}.
     */
    @Override
    public void saveRole(final Role role) {
        roleRepository.save(role);
    }
}
