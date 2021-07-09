Feature: Delete pet from store

  Scenario: Delete a pet
    Given that exists a pet named "Clark" with status "available"
    And its status has been updated to "sold"
    When the service is requested to delete this pet
    Then the pet should no longer exist