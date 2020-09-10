package com.repository;

import com.domain.SuitableAd;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link SuitableRepository} interface serves to simplify the data access process for {@link SuitableAd}
 * in database,binds database to implementation part has extended CRUD methods from {@link JpaRepository}
 *
 * @author Yuriy Bahlay
 * @version 1.1
 */

public interface SuitableRepository extends JpaRepository<SuitableAd, Integer> {
}
