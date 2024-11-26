package io.github.tkaczenko.auditor.core.internal.service;

import io.github.tkaczenko.auditor.core.config.PropertiesConfig.PersistingProperties;
import io.github.tkaczenko.auditor.core.internal.dto.AuditRequestResponseDto;
import io.github.tkaczenko.auditor.core.internal.entity.AuditRequestResponse;
import io.github.tkaczenko.auditor.core.internal.repository.AuditRequestResponseRepository;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** Provides methods for saving audit request and response data to the database. */
@Transactional
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditService {

  private final AuditRequestResponseRepository<? extends AuditRequestResponse>
      auditRequestResponseRepository;
  private final PersistingProperties persistingProperties;

  /**
   * Extends the destination object, copying any relevant context information from the Mapped
   * Diagnostic Context (MDC).
   *
   * @param destination the destination object to copy properties to
   */
  private static void extendFromMdc(Object destination) {
    Map<String, Object> contextMap =
        MDC.getCopyOfContextMap().entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    log.debug("Extracted from MDC: {}", contextMap);

    BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(destination);
    wrapper.setPropertyValues(
        new MutablePropertyValues(
            new MapPropertySource("customProperties", contextMap).getSource()),
        true);

    log.debug("Extended: {}", destination);
  }

  /**
   * Extends the provided source DTO to the destination object, copying all properties.
   *
   * @param source the source DTO to copy properties from
   * @param destination the destination object to copy properties to
   */
  private static void extendFromSource(AuditRequestResponseDto source, Object destination) {
    BeanUtils.copyProperties(source, destination);
    log.debug("Extended: {}", destination);
  }

  /**
   * Saves the provided audit request and response data to the database.
   *
   * @param source the DTO containing the audit request and response data to be saved
   */
  public void save(AuditRequestResponseDto source) {
    if (log.isDebugEnabled()) {
      log.debug("Saving: {}", source);
    }

    AuditRequestResponse destination = auditRequestResponseRepository.createNewEntity();
    extendFromSource(source, destination);

    if (persistingProperties.isExtendableFromMdc()) {
      extendFromMdc(destination);
    }

    AuditRequestResponse saved = auditRequestResponseRepository.save(destination);

    if (log.isDebugEnabled()) {
      log.debug("Saved: requestId {}, {}", saved.getRequestId(), saved);
    }
  }
}
