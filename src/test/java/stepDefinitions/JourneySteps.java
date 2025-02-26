package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.json.JSONObject;

public class JourneySteps {
    private static final String BASE_URL = "https://qa-interview-test.qa.splytech.dev/api";
    private RequestSpecification request;
    private Response response;
    private String journeyId;

    @Given("I have a valid journey request")
    public void i_have_a_valid_journey_request() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("departure_date", "2025-02-26T13:24:35.703Z");

        JSONObject pickup = new JSONObject();
        pickup.put("latitude", 51.5);
        pickup.put("longitude", -0.15);
        requestBody.put("pickup", pickup);

        JSONObject passenger = new JSONObject();
        passenger.put("name", "John Doe");
        passenger.put("phone_number", "+447594855513");
        requestBody.put("passenger", passenger);

        request = RestAssured.given().header("Content-Type", "application/json").body(requestBody.toString());
    }

    @When("I send a POST request to create a journey")
    public void i_send_a_post_request_to_create_a_journey() {
        response = request.post(BASE_URL + "/journeys");
    }

    @Then("The response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, response.getStatusCode(), "Unexpected status code");
    }

    @Then("The response should contain a journey ID")
    public void the_response_should_contain_a_journey_id() {
        journeyId = response.jsonPath().getString("_id");
        Assertions.assertNotNull(journeyId, "Journey ID is missing in response");
    }

    @Given("I have an invalid journey request with missing fields")
    public void i_have_an_invalid_journey_request_with_missing_fields() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("departure_date", "2025-02-26T13:24:35.703Z");

        request = RestAssured.given().header("Content-Type", "application/json").body(requestBody.toString());
    }

    @When("I send a GET request to retrieve the journey")
    public void i_send_a_get_request_to_retrieve_the_journey() {
        response = RestAssured.given().get(BASE_URL + "/journeys/" + journeyId);
    }

    @Then("The response should contain the correct journey details")
    public void the_response_should_contain_the_correct_journey_details() {
        Assertions.assertEquals("2025-02-26T13:24:35.703Z", response.jsonPath().getString("departure_date"), "Incorrect departure date");
    }

    @When("I send a GET request with an invalid journey ID")
    public void i_send_a_get_request_with_an_invalid_journey_id() {
        response = RestAssured.given().get(BASE_URL + "/journeys/invalid_id");
    }

    @Then("The response should return an error")
    public void the_response_should_return_an_error() {
        Assertions.assertNotNull( response.jsonPath().getString("message"));
    }

}
