package com.ssandri.stepdefinitions;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import com.ssandri.dto.MessageResponse;
import com.ssandri.dto.Pet;
import com.ssandri.dto.Pet.Builder;
import com.ssandri.services.petstore.PetResource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.List;

public class PetSteps {

  private final PetResource petResource = new PetResource();
  private Response response;
  private Pet expectedPet;
  private Pet actualPet;

  // GIVEN STEPS

  @Given("that exists a pet named {string} with status {string}")
  public void thatExistsAPetNamedWithStatus(String petName, String petStatus) {

    Pet newPet = new Builder()
        .withName(petName)
        .withStatus(petStatus)
        .build();

    actualPet = petResource.createPet(newPet).as(Pet.class);
  }

  @And("its status has been updated to {string}")
  public void itsStatusHasBeenUpdatedTo(String petStatus) {
    this.theServiceIsRequestedToUpdateThatPetToStatus(petStatus);
  }

  // WHEN STEPS

  @When("the service is requested to create a new pet named {string} with status {string}")
  public void theServiceIsRequestedToCreateANewPetNamedWithStatus(String petName, String petStatus) {
    expectedPet = new Pet
        .Builder()
        .withName(petName)
        .withStatus(petStatus)
        .build();

    response = petResource.createPet(expectedPet);
  }

  @When("the service is requested the list of pets that are {string}")
  public void theServiceIsRequestedTheListOfPetsThatAre(String expectedStatus) {
    response = petResource.findPetsByStatus(expectedStatus);
  }

  @When("the service is requested to update that pet to status {string}")
  public void theServiceIsRequestedToUpdateThatPetToStatus(String petStatus) {
    expectedPet = new Pet
        .Builder()
        .withName(actualPet.getName())
        .withId(actualPet.getId())
        .withStatus(petStatus)
        .build();
    response = new PetResource().updatePet(expectedPet);
  }

  @When("the service is requested to delete this pet")
  public void theServiceIsRequestedToDeleteThisPet() {
    response = new PetResource().deletePet(expectedPet.getId());
  }

  // THEN STEPS

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

    Pet responsePet = response.as(Pet.class);
    assertEquals(responsePet.getName(), expectedPet.getName());
    assertEquals(responsePet.getStatus(), expectedPet.getStatus());
  }

  @Then("the pet should change its status to {string}")
  public void thePetShouldChangeItsStatusTo(String expectedStatus) {

    Pet responsePet = response.as(Pet.class);
    assertEquals(responsePet.getStatus(), expectedStatus);

  }

  @Then("the pet should no longer exist")
  public void thePetShouldNoLongerExist() {

    assertEquals(response.getStatusCode(), 200);
    MessageResponse messageResponse = petResource.getPet(expectedPet.getId()).as(MessageResponse.class);
    assertEquals(messageResponse.getCode(), 1);
    assertEquals(messageResponse.getMessage(), "Pet not found");
    assertEquals(messageResponse.getType(), "error");
  }
}
