package com.kodecamp.db.student;

import java.util.Optional;

import com.kodecamp.db.commons.ReadOnlyEntityManager;

public class ReadOnlyDbStudentEntityManagerImpl implements ReadOnlyEntityManager<DbStudentEntity> {

  private DbStudentEnityManger em;

  public ReadOnlyDbStudentEntityManagerImpl(final DbStudentEnityManger studentEM) {
    this.em = studentEM;
  }

  @Override
  public Optional<DbStudentEntity> getObjectBy(Object id) {
    return this.em.getBy(id.toString());
  }
}
