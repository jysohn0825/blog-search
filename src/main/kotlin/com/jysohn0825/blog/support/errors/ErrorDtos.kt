package com.jysohn0825.blog.support.errors

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val httpStatus: HttpStatus,
    val message: String? = ""
)

class OpenApiException(message: String? = null, exception: Throwable) : RuntimeException(message, exception)
