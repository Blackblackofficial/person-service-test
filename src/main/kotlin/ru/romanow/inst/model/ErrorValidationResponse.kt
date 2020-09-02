package ru.romanow.inst.model

data class ErrorValidationResponse(
    override var message: String?,
    private var errors: Map<String, String>?
) : ErrorResponse(message)