package com.ssandri.stepdefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import com.ssandri.dto.Pet;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.List;

public class PetSteps {

  private Response response;

  @When("the service is requested to create a new pet named {string} with status {string}")
  public void theServiceIsRequestedToCreateANewPetNamedWithStatus(String petName, String petStatus) {
    response = given().body()
        .when()
        .get("https://petstore.swagger.io/v2/pet/findByStatus")
        .then()
        .extract()
        .response();
  }

  @When("the service is requested the list of pets that are {string}")
  public void theServiceIsRequestedTheListOfPetsThatAre(String expectedStatus) {
    response = given().queryParam("status", expectedStatus)
        .when()
        .get("https://petstore.swagger.io/v2/pet/findByStatus")
        .then()
        .extract()
        .response();
  }

  @Then("the service should return a list of all pets that are {string}")
  public void theServiceShouldReturnAListOfAllPetsThatAre(String expectedStatus) {

    List<Pet> petList = response.jsonPath().getList(".", Pet.class);
    assertTrue(petList.stream().allMatch(pet -> pet.getStatus().equals(expectedStatus)),
        "Get "
            + expectedStatus
            + " pets validations failed. There was at least one pet where it's status was different than ["
            + expectedStatus
            + "],");
  }

  @Then("the new pet should be created")
  public void theNewPetShouldBeCreated() {

  }
}
