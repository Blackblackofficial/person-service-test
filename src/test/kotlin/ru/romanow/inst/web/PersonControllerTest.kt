package ru.romanow.inst.web

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.romanow.inst.asserts.PersonAssert
import ru.romanow.inst.constants.PERSON_REQUEST

internal class PersonControllerTest {
    private val regex = "/persons/(\\d+)".toRegex()

    @Test
    @DisplayName("Create, get and delete person")
    fun crudOperationTest() {
        val controller = PersonController()
        val assertObject = PersonAssert()

        val location = controller.createPerson(PERSON_REQUEST)
        val id = extractId(location).toInt()
        val person = controller.person(id)
        assertObject.checkEquals(person)
        controller.deletePerson(id)
    }

    @Test
    @DisplayName("Get list of persons")
    fun personListTest() {
        val controller = PersonController()
        val assertObject = PersonAssert()

        var persons = controller.listPersons()
        val initialSize = persons.size
        val location = controller.createPerson(PERSON_REQUEST)
        val id = extractId(location).toInt()
        val person = controller.person(id)

        persons = controller.listPersons()
        assertObject.checkNewPersonAdded(person, persons, initialSize)
        controller.deletePerson(id)
    }

    private fun extractId(location: String): String =
        regex.find(location)?.groups?.get(1)?.value!!
}