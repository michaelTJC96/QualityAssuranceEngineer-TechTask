Feature: Journey API Testing

  Scenario: Create a journey with valid data
    Given I have a valid journey request
    When I send a POST request to create a journey
    Then The response status code should be 200
    And The response should contain a journey ID

  Scenario: Create a journey with missing required fields
    Given I have an invalid journey request with missing fields
    When I send a POST request to create a journey
    Then The response status code should be 400
    And The response should return an error

  Scenario: Retrieve an existing journey
    Given I have a valid journey request
    When I send a POST request to create a journey
    Then The response status code should be 200
    And The response should contain a journey ID
    When I send a GET request to retrieve the journey
    Then The response status code should be 200
    And The response should contain the correct journey details

  Scenario: Retrieve a journey with an invalid journey ID
    When I send a GET request with an invalid journey ID
    Then The response status code should be 400
    And The response should return an error
