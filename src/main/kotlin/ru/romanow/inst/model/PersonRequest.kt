package ru.romanow.inst.model

data class PersonRequest(
    val name: String,
    val age: Int?,
    val address: String?,
    val work: String?
)
