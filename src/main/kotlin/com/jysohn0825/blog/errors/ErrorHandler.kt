package com.jysohn0825.blog.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse?> {
        val response = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
        return ResponseEntity<ErrorResponse?>(response, response.httpStatus)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<ErrorResponse?> {
        val response = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
        return ResponseEntity<ErrorResponse?>(response, response.httpStatus)
    }

}
