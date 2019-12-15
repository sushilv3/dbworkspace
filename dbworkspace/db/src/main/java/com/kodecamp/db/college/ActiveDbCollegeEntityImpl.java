package com.kodecamp.db.college;

import java.util.ArrayList;
import java.util.Collection;

import com.kodecamp.db.student.DbStudentEntity;

public class ActiveDbCollegeEntityImpl implements ActiveDbCollegeEntity {

  private String name;
  private String address;
  private DbCollegeEntity lazyInstance;
  private Collection<DbStudentEntity> students = new ArrayList<>();

  public ActiveDbCollegeEntityImpl(DbCollegeEntity lazyInstance) {
    this.lazyInstance = lazyInstance;
  }
   
  public ActiveDbCollegeEntityImpl(
      DbCollegeEntity lazyInstance, final String name, final String address) {
    if (lazyInstance == null) {
      throw new RuntimeException("Not a valid entity : Please supply the Lazy Instance.");
    }
    this.lazyInstance = lazyInstance;
    this.name = name;
    this.address = address;
   
  }

  @Override
  public boolean delete() {

    return false;
  }

  @Override
  public boolean rename(String newName) {

    return false;
  }

  @Override
  public boolean changeAddress(String newAddress) {
    this.lazyInstance.changeAddress(newAddress);
    return false;
  }

  @Override
  public String name() {
    if ("".equals(this.name)) {
      // go to db and fetch the name
      this.name = this.lazyInstance.name();
    }
    return this.name;
  }

  @Override
  public String address() {
    if ("".equals(this.address)) {
      this.address = this.lazyInstance.address();
    }
    return this.address;
  }

  @Override
  public DbCollegeEntity lazyInstance() {
    return this.lazyInstance;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ActiveDbCollegeEntityImpl other = (ActiveDbCollegeEntityImpl) obj;
    if (address == null) {
      if (other.address != null) return false;
    } else if (!address.equals(other.address)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  @Override
  public Collection<DbStudentEntity> students() {
    if (this.students.isEmpty()) {
      this.students.addAll(this.lazyInstance.students());
    }
    return this.students;
  }
}
