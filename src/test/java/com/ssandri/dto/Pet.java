package com.ssandri.dto;

import java.util.List;

public class Pet {
  private Long id;
  private Category category;
  private String name;
  private List<String> photoUrls;
  private List<Tag> tags;
  private String status;

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
}
