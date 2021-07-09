package com.ssandri.dto;

import java.util.List;

public class Pet {
  private Long id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<Tag> tags;
  private String status;

  private Pet() {}

  public Long getId() {

    return id;
  }

  public Category getCategory() {

    return category;
  }

  public String getName() {

    return name;
  }

  public List<String> getPhotoUrls() {

    return photoUrls;
  }

  public List<Tag> getTags() {

    return tags;
  }

  public String getStatus() {

    return status;
  }

  public static class Builder {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Builder withName(String name) {

      this.name = name;
      return this;
    }

    public Builder withId(Long id) {

      this.id = id;
      return this;
    }

    public Builder withCategory(Category category) {

      this.category = category;
      return this;
    }

    public Builder withPhotoUrls(List<String> photoUrls) {

      this.photoUrls = photoUrls;
      return this;
    }

    public Builder withTags(List<Tag> tags) {

      this.tags = tags;
      return this;
    }

    public Builder withStatus(String status) {

      this.status = status;
      return this;
    }

    public Pet build() {

      Pet pet = new Pet();
      pet.id = this.id;
      pet.category = this.category;
      pet.name = this.name;
      pet.photoUrls = this.photoUrls;
      pet.status = this.status;
      pet.tags = this.tags;

      return pet;
    }
  }
}
