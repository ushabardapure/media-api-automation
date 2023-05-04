package com.api.automation.junit.tests;

import com.api.test.automation.util.Util;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsMapWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;


/**
 * Junit test to verify that find and finAll expressions are tested with all the possible variations of data inputs
 */
@RunWith(MockitoJUnitRunner.class)
public class APITestAutomationStepDefinitionsTest {

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    private static final String EXPRESSION_FIND_ID_WITH_NULL_EMPTY = "data.find { it.id == null || it.id == '' }";
    private static final String EXPRESSION_FIND_FIELD_NOT_HAVING_GIVEN_VALUE = "data.find { it.segment_type != 'music' }";
    private static final String EXPRESSION_ONLY_ONE_FIELD_HAS_GIVEN_VALUE = "data.findAll { it.offset.now_playing == true }";

    @Test
    public void testFindExpressionWithValidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test1.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        Map<Object, Object> result = Util.find(response, EXPRESSION_FIND_ID_WITH_NULL_EMPTY);
        assertThat(result, nullValue());
    }

    @Test
    public void testFindExpressionWithInValidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test2.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        Map<Object, Object> result = Util.find(response, EXPRESSION_FIND_ID_WITH_NULL_EMPTY);
        assertThat(result, is(not(nullValue())));
        assertThat(result, IsMapWithSize.aMapWithSize(8));
    }

    @Test
    public void testEveryFieldShouldHaveValueWithValidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test4.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        Map<Object, Object> result = Util.find(response, EXPRESSION_FIND_FIELD_NOT_HAVING_GIVEN_VALUE);
        assertThat(result, nullValue());
    }

    @Test
    public void testEveryFieldShouldHaveValueWithInvalidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test3.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        Map<Object, Object> result = Util.find(response, EXPRESSION_FIND_FIELD_NOT_HAVING_GIVEN_VALUE);
        assertThat(result, is(not(nullValue())));
        assertThat(result, IsMapWithSize.aMapWithSize(8));
    }

    @Test
    public void testOnlyOneFieldHasGivenValueWithValidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test5.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        List<Map<Object, Object>> result = Util.findAll(response, EXPRESSION_ONLY_ONE_FIELD_HAS_GIVEN_VALUE);
        assertThat(result, Matchers.hasSize(1));
    }

    @Test
    public void testOnlyOneFieldHasGivenValueWithInValidInput() throws IOException {
        when(response.getBody()).thenReturn(responseBody);
        String dataInput = new String(Files.readAllBytes(Paths.get("src/test/resources/junit/mockdata/test6.json")));
        when(responseBody.jsonPath()).thenReturn(JsonPath.from(dataInput));
        List<Map<Object, Object>> result = Util.findAll(response, EXPRESSION_ONLY_ONE_FIELD_HAS_GIVEN_VALUE);
        assertThat(result, IsEmptyCollection.empty());
    }
}
