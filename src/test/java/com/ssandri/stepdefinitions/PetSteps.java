package com.ssandri.stepdefinitions;

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
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

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

    Pet modifiedPet = new Builder()
        .withName(actualPet.getName())
        .withId(actualPet.getId())
        .withStatus(petStatus)
        .build();
    response = new PetResource().updatePet(modifiedPet);
  }

  @When("the service is requested to delete this pet")
  public void theServiceIsRequestedToDeleteThisPet() {
    response = new PetResource().deletePet(actualPet.getId());
  }

  // THEN STEPS

  @Then("the service should return a list of all pets that are {string}")
  public void theServiceShouldReturnAListOfAllPetsThatAre(String expectedStatus) {

    assertEquals(response.getStatusCode(), 200,
        "Get Pet by status response http status code validation failed.");
    SoftAssert softAssert = new SoftAssert();
    List<Pet> petList = response.jsonPath().getList(".", Pet.class);
    softAssert.assertTrue(petList.stream().allMatch(pet -> pet.getStatus().equals(expectedStatus)),
        "Get "
            + expectedStatus
            + " pets validations failed. There was at least one pet where it's status was different than ["
            + expectedStatus
            + "],");
    softAssert.assertAll();
  }

  @Then("the new pet should be created")
  public void theNewPetShouldBeCreated() {

    assertEquals(response.getStatusCode(), 200,
        "Create Pet response http status code validation failed.");
    SoftAssert softAssert = new SoftAssert();
    Pet responsePet = response.as(Pet.class);

    softAssert.assertEquals(responsePet.getName(), expectedPet.getName(),
        "Create Pet response pet name validation failed.");
    softAssert.assertEquals(responsePet.getStatus(), expectedPet.getStatus(),
        "Create Pet response pet status validation failed.");
    softAssert.assertAll();
  }

  @Then("the pet should change its status to {string}")
  public void thePetShouldChangeItsStatusTo(String expectedStatus) {

    assertEquals(response.getStatusCode(), 200,
        "Update Pet response http status code validation failed.");
    SoftAssert softAssert = new SoftAssert();
    Pet responsePet = response.as(Pet.class);
    softAssert.assertEquals(responsePet.getStatus(), expectedStatus,
        "Update Pet response pet status validation failed.");
    softAssert.assertAll();

  }

  @Then("the pet should no longer exist")
  public void thePetShouldNoLongerExist() {

    assertEquals(response.getStatusCode(), 200,
        "Delete response http status code validation failed.");
    SoftAssert softAssert = new SoftAssert();
    MessageResponse deleteMessage = response.as(MessageResponse.class);
    softAssert.assertEquals(deleteMessage.getCode(), 200,
        "Delete response message code validation failed.");
    softAssert.assertEquals(deleteMessage.getMessage(), actualPet.getId().toString(),
        "Delete response message validation failed.");

    Response getPetResponse = petResource.getPet(actualPet.getId());
    MessageResponse getPetMessage = getPetResponse.as(MessageResponse.class);

    assertEquals(getPetResponse.getStatusCode(), 404,
        "Get Pet by id response http status code validation failed.");
    softAssert.assertEquals(getPetMessage.getCode(), 1,
        "Get Pet by id response message code validation failed.");
    softAssert.assertEquals(getPetMessage.getMessage(),
        "Pet not found", "Get Pet by id response message validation failed.");
    softAssert.assertEquals(getPetMessage.getType(), "error",
        "Get Pet by id response message type validation failed.");
    softAssert.assertAll();
  }
}
