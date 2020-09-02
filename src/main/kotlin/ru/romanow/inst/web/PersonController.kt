package ru.romanow.inst.web

import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import io.restassured.response.ResponseBodyExtractionOptions
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import ru.romanow.inst.model.PersonResponse

inline fun <reified T> ResponseBodyExtractionOptions.to(): T {
    return this.`as`(T::class.java)
}

class PersonController {
    private val requestSpecification: RequestSpecification = RequestSpecBuilder()
        .setBaseUri("https://rsoi-person-service.herokuapp.com")
        .setBasePath("/calc")
        .setAccept(ContentType.JSON)
        .log(LogDetail.ALL)
        .build()

    init {
        RestAssured.defaultParser = Parser.JSON
        RestAssured.responseSpecification = ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectResponseTime(Matchers.lessThan(15000L))
            .build()
    }

    @Step("Get person")
    fun person(id: Int): PersonResponse =
        given(requestSpecification)
            .pathParam("id", id)
            .get("/persons/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .to()

    @Step("List persons")
    fun listPersons(): List<PersonResponse> =
        given(requestSpecification)
            .get("/persons")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .to()
}