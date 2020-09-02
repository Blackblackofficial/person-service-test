package ru.romanow.inst.model

data class PersonResponse(
    val id: Int,
    val name: String,
    val age: Int?,
    val address: String?,
    val work: String?
)
