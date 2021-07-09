Feature: Update pet

  Scenario: Update a pet to status sold
    Given that exists a pet named "Clark" with status "available"
    When the service is requested to update that pet to status "sold"
    Then the pet should change its status to "sold"