Feature: Test an API endpoint

  Background:
    Given API Endpoint "https://testapi.io/api/ottplatform/media"
    When HTTP GET Request is invoked
    Then HTTP Response is received with status code 200

  Scenario: Verify that value of the "field" should not be empty or null
    And Verify that value of the "field" should not be empty or null
      | field |
      | id    |

  Scenario: Verify that response time should be within a given time period
    And Verify that response time should be within "responseTime" milliseconds
      | responseTime |
      | 1000         |

  Scenario: Verify that value of the field should not be empty or null
    And Verify that value of the "field" should not be empty or null
      | field              |
      | title_list.primary |

  Scenario: Verify that every field should have a given value
    And Verify that every "fieldName" should have a given "fieldValue"
      | fieldName    | fieldValue |
      | segment_type | music      |

  Scenario: Verify that only one field has a given value
    And Verify that only one "fieldName" has a given "fieldValue"
      | fieldName          | fieldValue |
      | offset.now_playing | true       |

  Scenario: Verify that field is present in response header
    And Verify that response header should have a "fieldName"
      | fieldName |
      | Date      |
