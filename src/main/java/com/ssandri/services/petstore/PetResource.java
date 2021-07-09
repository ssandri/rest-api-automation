package com.ssandri.services.petstore;

import static io.restassured.RestAssured.*;
import static io.restassured.http.Method.DELETE;
import static io.restassured.http.Method.GET;
import static io.restassured.http.Method.POST;
import static io.restassured.http.Method.PUT;
import static io.restassured.parsing.Parser.*;

import com.ssandri.dto.Pet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetResource {

  private final RequestSpecification requestSpecification;

  public PetResource() {

    baseURI = "https://petstore.swagger.io/v2";
    basePath = "/pet";
    defaultParser = JSON;
    requestSpecification = new RequestSpecBuilder()
        .setContentType("application/json")
        .build();
  }

  public Response findPetsByStatus(String status) {
    return given().spec(requestSpecification).queryParam("status", status).request(GET, "/findByStatus");
  }

  public Response createPet(Pet pet) {
    return given().spec(requestSpecification).body(pet).request(POST);
  }

  public Response updatePet(Pet pet) {

    return given().spec(requestSpecification).body(pet).request(PUT);
  }

  public Response deletePet(Long id) {
    return given().spec(requestSpecification).when().request(DELETE, "/{petId}", id);
  }

  public Response getPet(Long id) {
    return given().spec(requestSpecification).request(GET, "/{petId}", id);
  }
}
