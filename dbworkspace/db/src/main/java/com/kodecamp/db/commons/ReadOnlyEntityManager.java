package com.kodecamp.db.commons;

import java.util.Optional;

public interface ReadOnlyEntityManager<T> {
  public Optional<T> getObjectBy(final Object id);
}
