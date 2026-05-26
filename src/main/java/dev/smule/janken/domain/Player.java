package dev.smule.janken.domain;

public record Player(String id, String name) {

  public Player {
    id = requireNonNullOrBlank(id, "id");
    name = requireNonNullOrBlank(name, "id");
  }

  private static String requireNonNullOrBlank(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " must not be blank");
    }
    return value.trim();
  }
}
