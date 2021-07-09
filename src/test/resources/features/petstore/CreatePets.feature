Feature: Create pets

  Scenario: Create an available pet
    When the service is requested to create a new pet named "Harry" with status "available"
    Then the new pet should be created