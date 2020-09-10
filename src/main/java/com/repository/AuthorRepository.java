package com.repository;

import com.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link AuthorRepository} interface serves to simplify the data access process for {@link Author} in database,
 * binds database to implementation part,has extends CRUD methods from {@link JpaRepository} and queries
 * for getting need additional data from database
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
