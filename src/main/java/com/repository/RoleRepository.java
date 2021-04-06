package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * This is {@link RoleRepository} interface it helps us
 * to access to database.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Repository
public interface RoleRepository extends
        JpaRepository<Role, Integer> {
}
