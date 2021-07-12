package com.ssandri.utils;


import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAssuredFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger("rest-api-automation");

  @Override
  public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
      FilterContext ctx) {

    Response response = ctx.next(requestSpec, responseSpec);
    LOGGER.info(requestSpec.getMethod() + " " + requestSpec.getURI() + " \n Request Body: " + requestSpec.getBody()
        + "\n Response Status => " + response.getStatusCode() + " " + response.getStatusLine() + " \n Response Body: \n "
        + response.getBody().prettyPrint());
    return response;
  }
}
