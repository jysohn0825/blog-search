package com.jysohn0825.blog.support.errors

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val httpStatus: HttpStatus,
    val message: String? = ""
)
