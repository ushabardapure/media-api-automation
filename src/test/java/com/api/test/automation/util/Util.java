package com.api.test.automation.util;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class Util {

    /**
     * Method used to find first occurrence of the object matching with the criteria given in the filter expression.
     * E.g. data.find{ it.field_name == 'field_value' }
     *
     * @param response
     * @param filterExpression
     * @return If found then return Map representing JSON object otherwise null
     */
    public static Map<Object, Object> find(Response response, String filterExpression) {
        return response.getBody().jsonPath().get(filterExpression);
    }


    /**
     * Method used to find all the occurrences of the object matching with the criteria given in the filter expression.
     * E.g. data.findAll{ it.field_name == 'field_value' }
     *
     * @param response
     * @param filterExpression
     * @return If found then collection of items matching criteria otherwise empty collection
     */
    public static List<Map<Object, Object>> findAll(Response response, String filterExpression) {
        return response.getBody().jsonPath().get(filterExpression);
    }
}
