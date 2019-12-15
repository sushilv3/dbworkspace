package com.kodecamp.db.course;

public class ActiveDbCourseEntityImpl implements ActiveDbCourseEntity {
	private String name;
  private int seats;
	private int availableSeats;
  private DbCourseEntity lazyInstance;

  public ActiveDbCourseEntityImpl(
      DbCourseEntity lazyInstance, String name, int seats, int availableSeats) {
    if (lazyInstance == null) {
      throw new RuntimeException("Not a valid entity : Please supply the Lazy Instance.");
    }
    this.name = name;
    this.seats = seats;
    this.availableSeats = availableSeats;
    this.lazyInstance = lazyInstance;
  }

  public ActiveDbCourseEntityImpl(DbCourseEntity lazyInstance) {
    if (lazyInstance == null) {
      throw new RuntimeException("Not a valid entity : Please supply the Lazy Instance.");
    }
    this.lazyInstance = lazyInstance;
  }

  @Override
  public boolean rename(String newName) {

    return this.lazyInstance.rename(newName);
  }

  @Override
  public boolean updateSeats(int newValue) {

    return this.lazyInstance.updateSeats(newValue);
  }

  @Override
  public String name() {
    if ("".equals(this.name)) {
      this.name = this.lazyInstance.name();
    }
    return this.name;
  }

  @Override
  public int seats() {
    if ("".equals(this.seats)) {
      this.seats = this.lazyInstance.seats();
    }
    return this.seats;
  }

  @Override
  public int availbaleSeats() {
    if ("".equals(this.availableSeats)) {}
    this.availableSeats = this.lazyInstance.availbaleSeats();
    return this.availableSeats;
  }

  @Override
  public DbCourseEntity lazyIntance() {
    return this.lazyInstance;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + availableSeats;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + seats;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ActiveDbCourseEntityImpl other = (ActiveDbCourseEntityImpl) obj;
    if (availableSeats != other.availableSeats) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (seats != other.seats) return false;
    return true;
  }
}
