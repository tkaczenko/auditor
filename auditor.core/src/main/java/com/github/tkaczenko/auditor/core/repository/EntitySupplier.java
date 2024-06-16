package com.github.tkaczenko.auditor.core.repository;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides a way to create new instances of the entity class associated with the repository. The
 * entity class is determined dynamically based on the generic type parameters of the repository
 * interface.
 *
 * @param <T> type of the entity
 * @param <K> type of the entity's primary key
 */
public interface EntitySupplier<T, K> extends JpaRepository<T, K> {

  /**
   * This map caches the entity class associated with each repository instance. This allows the
   * {@link #createNewEntity()} method to quickly retrieve the entity class without having to
   * determine it dynamically each time.
   */
  Map<Object, Class<?>> entityClassCache = new ConcurrentHashMap<>();

  private static Type[] getGenericType(Class<?> target) {
    if (target == null) {
      return new Type[0];
    }
    Type[] types = target.getGenericInterfaces();
    if (types.length > 0) {
      return types;
    }
    Type type = target.getGenericSuperclass();
    if (type instanceof ParameterizedType) {
      return new Type[] {type};
    }
    return new Type[0];
  }

  private static Class<?> getClass(Type type) {
    if (type instanceof Class) {
      return (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      return getClass(((ParameterizedType) type).getRawType());
    } else if (type instanceof GenericArrayType) {
      Type componentType = ((GenericArrayType) type).getGenericComponentType();
      Class<?> componentClass = getClass(componentType);
      return componentClass != null ? Array.newInstance(componentClass, 0).getClass() : null;
    } else {
      return null;
    }
  }

  private static Class<?> getEntityClass(Object repository) {
    return entityClassCache.computeIfAbsent(
        repository,
        key -> {
          Type[] genericTypes = getGenericType(key.getClass());
          if (genericTypes.length > 0) {
            Type[] entityTypes = getGenericType(getClass(genericTypes[0]));
            ParameterizedType parameterizedType = (ParameterizedType) entityTypes[0];
            return getClass(parameterizedType.getActualTypeArguments()[0]);
          }
          return null;
        });
  }

  @SneakyThrows
  private static <T> T createNewEntity(Object repository) {
    Class<?> entityClass = getEntityClass(repository);
    if (entityClass != null) {
      return (T) entityClass.getDeclaredConstructor().newInstance();
    }
    throw new IllegalStateException("Cannot determine entity class for repository: " + repository);
  }

  /**
   * Provides a way to create new instances of the entity class associated with the repository. The
   * entity class is determined dynamically based on the generic type parameters of the repository
   * interface.
   *
   * @return a new instance of the entity class
   */
  default T createNewEntity() {
    return createNewEntity(this);
  }
}
