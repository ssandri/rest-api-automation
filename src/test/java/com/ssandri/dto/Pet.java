package com.ssandri.dto;

import java.util.List;

public class Pet {
  public Long id;
  public Category category;
  public String name;
  public List<String> photoUrls;
  public List<Tag> tags;
  public String status;

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
