package ru.romanow.inst.web

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class PersonControllerTest {
    companion object {
        const val PERSON_ID = 1
    }

    @Test
    @DisplayName("Get person")
    fun plusTest() {
        val result = PersonController().person(PERSON_ID)
    }

    @Test
    @DisplayName("Get list of persons")
    fun minusTest() {
        val result = PersonController().listPersons()
    }
}