Feature: Test cases for the manual testing of REST API Endpoint

  Background:
    Given API Endpoint URL
    When HTTP GET request is invoked using HTTP clients like Postman and Web Browser
    Then Verify that valid HTTP response is received in the JSON format


  Scenario: To verify that appropriate music track is played
    And Pick any link listed in the "uris" field from the response
    And Open that link in the browser
    Then  Verify that music played is appropriate and as per "primary" and "secondary" field


  Scenario: To verify that start offset of the given track is greater than the end offset of the previous track
    And  Pick any track item from the JSON response
    Then Verify that the value of the "offset.start" field is greater than the value of "offset.end" of the previous track


  Scenario: To verify that there are no tracks with the overlapping offsets
    And Take a good look at all the "offset.start" and "offset.end" fields
    Then  Verify that there are no tracks with overlapping offsets


  Scenario: To verify that "start" and "end" offset should not be same
    And Take a good look at all the "offset.start" and "offset.end" fields
    Then  Verify that there are no tracks with same "start" and "end" offset values


  Scenario: To verify that the uri list is not empty
    And Take a good look at all the "uris" fields
    Then Verify that the uri list is not empty for any of the track

