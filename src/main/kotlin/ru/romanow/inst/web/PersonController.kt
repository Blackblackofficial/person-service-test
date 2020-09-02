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
import org.apache.http.HttpHeaders
import org.apache.http.HttpStatus
import org.hamcrest.Matchers
import ru.romanow.inst.model.PersonRequest
import ru.romanow.inst.model.PersonResponse
import ru.romanow.inst.model.PersonResponseList

class PersonController {
    private val requestSpecification: RequestSpecification = RequestSpecBuilder()
        .setBaseUri("https://rsoi-person-service.herokuapp.com")
        .setBasePath("/persons")
        .setAccept(ContentType.JSON)
        .log(LogDetail.ALL)
        .build()

    init {
        RestAssured.defaultParser = Parser.JSON
        RestAssured.responseSpecification = ResponseSpecBuilder()
            .expectResponseTime(Matchers.lessThan(15000L))
            .build()
    }

    @Step("Get person")
    fun person(id: Int): PersonResponse =
        given(requestSpecification)
            .pathParam("id", id)
            .get("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(PersonResponse::class.java)

    @Step("List persons")
    fun listPersons(): List<PersonResponse> =
        given(requestSpecification)
            .get()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .extract()
            .body()
            .`as`(PersonResponseList::class.java)

    @Step("Create person")
    fun createPerson(request: PersonRequest): String =
        given(requestSpecification)
            .body(request)
            .contentType(ContentType.JSON)
            .post()
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .header(HttpHeaders.LOCATION)
            .toString()

    @Step("Delete person")
    fun deletePerson(id: Int) =
        given(requestSpecification)
            .pathParam("id", id)
            .get("/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
}