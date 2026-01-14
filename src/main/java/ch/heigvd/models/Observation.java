package ch.heigvd.models;

import java.time.LocalDate;

public class Observation {

  private int id;
  private int animalNumber;
  private LocalDate date;
  private String region;
  private String notes;

  public Observation() {}

  public Observation(int id, int animalNumber, LocalDate date, String region, String notes) {
    this.id = id;
    this.animalNumber = animalNumber;
    this.date = date;
    this.region = region;
    this.notes = notes;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAnimalNumber() {
    return animalNumber;
  }

  public void setAnimalNumber(int animalNumber) {
    this.animalNumber = animalNumber;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Observation that = (Observation) o;
    return id == that.id
        && animalNumber == that.animalNumber
        && java.util.Objects.equals(date, that.date)
        && java.util.Objects.equals(region, that.region)
        && java.util.Objects.equals(notes, that.notes);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(id, animalNumber, date, region, notes);
  }
}
