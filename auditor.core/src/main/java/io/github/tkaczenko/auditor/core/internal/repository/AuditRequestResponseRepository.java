package io.github.tkaczenko.auditor.core.internal.repository;

import io.github.tkaczenko.auditor.core.internal.entity.AuditRequestResponse;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Provides methods for interacting with the AuditRequestResponse entity in the database.
 *
 * @param <T> the type of the AuditRequestResponse entity
 */
@NoRepositoryBean
public interface AuditRequestResponseRepository<T extends AuditRequestResponse>
    extends EntitySupplier<T, Long> {

  /**
   * Saves the given AuditRequestResponse entity to the database.
   *
   * @param <S> the type of the saved AuditRequestResponse entity
   * @param entity the AuditRequestResponse entity to be saved
   * @return the saved AuditRequestResponse entity
   */
  default <S extends T> S save(AuditRequestResponse entity) {
    return (S) save((T) entity);
  }
}
