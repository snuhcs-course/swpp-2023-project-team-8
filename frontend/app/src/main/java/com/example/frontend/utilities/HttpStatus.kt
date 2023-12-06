package com.example.frontend.utilities

enum class HttpStatus(val code: Int) {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    UNPROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504)
    ;

    companion object {
        fun fromCode(code: Int): HttpStatus {
            return values().find { it.code == code } ?: throw Exception("Invalid HTTP status code: $code")
        }
    }
}