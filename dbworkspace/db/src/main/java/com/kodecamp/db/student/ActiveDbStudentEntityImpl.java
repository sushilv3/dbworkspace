package com.kodecamp.db.student;

import java.util.Optional;

import com.kodecamp.db.commons.ReadOnlyEntityManager;

public class ActiveDbStudentEntityImpl implements ActiveDbStudentEntity {

  private String name;
  private String address;
  private String rollNo;
  private String collegeCode;
  private String courseCode;
  private int version;

  private DbStudentEntity lazyInstance;
  private ReadOnlyEntityManager<DbStudentEntity> rEm;

  public ActiveDbStudentEntityImpl(DbStudentEntity lazyInstance) {
    this.lazyInstance = lazyInstance;
  }

  public ActiveDbStudentEntityImpl(
      final ReadOnlyEntityManager<DbStudentEntity> rEm,
      final DbStudentEntity lazyInstance,
      final String name,
      final String address,
      final String rollNo,
      final String collegeCode,
      final String courseCode) {
    if (lazyInstance == null) {
      throw new RuntimeException("Not a valid entity : Please supply the Lazy Instance.");
  	}
    this.lazyInstance = lazyInstance;
    this.rEm = rEm;
    this.name = name;
    this.address = address;
    this.rollNo = rollNo;
    this.collegeCode = collegeCode;
    this.courseCode = courseCode;
    this.version = this.lazyInstance.version();
  }

  @Override
  public DbStudentEntity lazyInstance() {
    return this.lazyInstance;
  }

  @Override
  public boolean rename(String newName) {
    boolean isChanged = this.lazyInstance.rename(newName);
    this.name = isChanged ? newName : this.name;
    return isChanged;
  }

  @Override
  public boolean changeAddress(String newAddress) {
    boolean isChanged = this.lazyInstance.changeAddress(newAddress);
    this.address = isChanged ? newAddress : this.address;
    return isChanged;
  }

  @Override
  public boolean delete() {
    return this.lazyInstance.delete();
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
  public Id id() {
    return this.lazyInstance.id();
  }

  @Override
  public String rollNo() {
    if ("".equals(this.rollNo)) {
      this.rollNo = this.lazyInstance.rollNo();
    }
    return this.rollNo;
  }
 
  

  @Override
  public String collegeCode() {
    if ("".equals(this.collegeCode)) {
      this.collegeCode = this.lazyInstance.collegeCode();
    }
    return this.collegeCode;
  }

  @Override
  public String courseCode() {
    if ("".equals(this.courseCode)) {
      this.courseCode = this.lazyInstance.courseCode();
    }
    return this.courseCode;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((collegeCode == null) ? 0 : collegeCode.hashCode());
    result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((rollNo == null) ? 0 : rollNo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ActiveDbStudentEntityImpl other = (ActiveDbStudentEntityImpl) obj;
    if (address == null) {
      if (other.address != null) return false;
    } else if (!address.equals(other.address)) return false;
    if (collegeCode == null) {
      if (other.collegeCode != null) return false;
    } else if (!collegeCode.equals(other.collegeCode)) return false;
    if (courseCode == null) {
      if (other.courseCode != null) return false;
    } else if (!courseCode.equals(other.courseCode)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (rollNo == null) {
      if (other.rollNo != null) return false;
    } else if (!rollNo.equals(other.rollNo)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "ActiveDbStudentEntityImpl [id = "
        + this.lazyInstance.id().value()
        + "name="
        + name
        + ", address="
        + address
        + ", rollNo="
        + rollNo
        + ", collegeCode="
        + collegeCode
        + ", courseCode="
        + courseCode
        + ", version="
        + this.version
        + "]";
  }

  /** Execute this method when required since it is very expensive in nature. */
  @Override
  public void refresh() {

    Optional<DbStudentEntity> freshEntityOpt = this.rEm.getObjectBy(this.lazyInstance.id().value());
    DbStudentEntity freshEntity =
        freshEntityOpt.orElseThrow(
            () -> new RuntimeException("Something went wrong while refreshing."));
    this.name = freshEntity.name();
    this.address = freshEntity.address();
    this.rollNo = freshEntity.rollNo();
    this.version = freshEntity.version();
    this.lazyInstance.version(this.version);
  }

  @Override
  public int version() {
    return this.version;
  }

  @Override
  public void version(int newVersion) {
    this.version = newVersion;
  }
}
