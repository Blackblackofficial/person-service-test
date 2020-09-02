package ru.romanow.inst.asserts

import org.junit.jupiter.api.Assertions
import ru.romanow.inst.constants.PERSON_REQUEST
import ru.romanow.inst.model.PersonRequest
import ru.romanow.inst.model.PersonResponse

class PersonAssert {
    fun checkEquals(response: PersonResponse) = checkEquals(PERSON_REQUEST, response)

    fun checkNewPersonAdded(person: PersonResponse, persons: List<PersonResponse>, initialSize: Int) {
        Assertions.assertEquals(initialSize + 1, persons.size)
        val personFromList = persons.find { it.id == person.id }!!
        checkEquals(PERSON_REQUEST, personFromList)
    }

    private fun checkEquals(expected: PersonRequest, actual: PersonResponse) {
        Assertions.assertEquals(expected.name, actual.name)
        Assertions.assertEquals(expected.age, actual.age)
        Assertions.assertEquals(expected.address, actual.address)
        Assertions.assertEquals(expected.work, actual.work)
    }
}
