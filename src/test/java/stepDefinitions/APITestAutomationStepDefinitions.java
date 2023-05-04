package stepDefinitions;

import com.api.test.automation.util.Util;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.core.IsNot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.lessThan;

public class APITestAutomationStepDefinitions {

    private URI endpointURL;
    public Response response;

    @Given("API Endpoint {string}")
    public void apiEndpoint(String url) throws URISyntaxException {
        endpointURL = new URI(url);
    }

    @And("HTTP GET Request is invoked")
    public void iSendHTTPGETRequest() {
        response = RestAssured.when().get(endpointURL).thenReturn();
    }

    @Then("HTTP Response is received with status code {int}")
    public void iReceiveHTTPResponseWithStatusCode(Integer statusCode) {
        assertThat(response.statusCode(), is(statusCode));
    }

    @And("Verify that response time should be within {string} milliseconds")
    public void verifyThatResponseTimeShouldBeWithinMilliseconds(String responseTime, DataTable dataTable) {
        List<Map<String, Long>> dataTableMaps = dataTable.asMaps(String.class, Long.class);
        Long expectedTimeOut = dataTableMaps.get(0).get(responseTime);
        String reason = String.format("Response time should be less than %d milliseconds", expectedTimeOut);
        assertThat(reason, response.getTimeIn(MILLISECONDS), lessThan(expectedTimeOut));
    }

    @And("Verify that value of the {string} should not be empty or null")
    public void verifyThatFieldShouldNotBeEmptyOrNull(String field, DataTable dataTable) {
        List<Map<String, String>> fields = dataTable.asMaps(String.class, String.class);
        fields.forEach(fieldMap -> {
            String filterExpression = "data.find { it." + fieldMap.get(field) + " == null || it." + fieldMap.get(field) + " == '' }";
            Map<Object, Object> result = Util.find(response, filterExpression);

            String reason = String.format("field %s is empty or null", fieldMap.get(field));
            assertThat(reason, result, nullValue());
        });
    }

    @And("Verify that every {string} should have a given {string}")
    public void verifyThatEveryFieldShouldHaveValue(String fieldName, String value, DataTable dataTable) {
        List<Map<String, String>> fields = dataTable.asMaps(String.class, String.class);
        String fieldAttributeName = fields.get(0).get(fieldName);
        String fieldValue = fields.get(0).get(value);

        fields.forEach(fieldMap -> {
            String filterExpression = "data.find { it." + fieldAttributeName + " != '" + fieldValue + "' }";
            Map<Object, Object> result = Util.find(response, filterExpression);

            String reason = String.format("field %s should have value %s", fieldAttributeName, fieldValue);
            assertThat(reason, result, nullValue());
        });
    }

    @And("Verify that only one {string} has a given {string}")
    public void verifyThatOnlyOneFieldHasGivenValue(String fieldName, String value, DataTable dataTable) {
        List<Map<String, String>> fields = dataTable.asMaps(String.class, String.class);
        String fieldAttributeName = fields.get(0).get(fieldName);
        String fieldValue = fields.get(0).get(value);

        fields.forEach(fieldMap -> {
            String filterExpression = "data.findAll { it." + fieldAttributeName + " == " + fieldValue + " }";
            List<Map<Object, Object>> result = Util.findAll(response, filterExpression);
            String reason = String.format("Only one field %s in the response should have value %s", fieldAttributeName, fieldValue);

            assertThat(reason, result, IsNot.not(empty()));
            assertThat(reason, result, hasSize(1));
        });
    }

    @And("Verify that response header should have a {string}")
    public void verifyThatResponseHeaderShouldHaveAField(String fieldName, DataTable dataTable) {
        List<Map<String, String>> fields = dataTable.asMaps(String.class, String.class);
        String reason = String.format("Response header should have %s field", fieldName);
        assertThat(reason, response.getHeader(fields.get(0).get(fieldName)), not(emptyOrNullString()));
    }

}
