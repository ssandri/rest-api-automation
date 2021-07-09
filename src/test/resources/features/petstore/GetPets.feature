Feature: Get pets on the pet store

  Scenario: Get available pets on the store
    When the service is requested the list of pets that are "available"
    Then the service should return a list of all pets that are "available"