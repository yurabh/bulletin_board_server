package com.repository;

import com.domain.Heading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link HeadingRepository} interface serves to simplify the data
 * access process for {@link Heading} in database,binds database to
 * implementation part has extended CRUD methods from {@link JpaRepository}.
 *
 * @author Yuriy Bahlay.
 * @version 1.1.
 */

@Repository
public interface HeadingRepository extends JpaRepository<Heading, Integer> {
}
