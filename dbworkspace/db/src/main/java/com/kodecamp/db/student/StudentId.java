package com.kodecamp.db.student;

public final class StudentId implements Id {
  private final String name;
  private final String address;
  private final String id;

  public StudentId(final String name, final String address) {
    this.name = name;
    this.address = address;
    this.id = "";
  }

  public StudentId(final String id) {
    this.name = "";
    this.address = "";
    this.id = id;
  }

  @Override
  public String value() {
    return "".equals(this.id) ? this.generateId() : this.id;
  }

  private String generateId() {
    return this.name + "_" + this.address;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    StudentId other = (StudentId) obj;
    if (address == null) {
      if (other.address != null) return false;
    } else if (!address.equals(other.address)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  public static void main(String args[]) {
    StudentId id1 = new StudentId("sunil", "naini");
    StudentId id2 = new StudentId("sunil", "naini1");
    System.out.println(id1.equals(id2));
  }
}
