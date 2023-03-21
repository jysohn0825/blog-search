package com.jysohn0825.blog.support.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getStringYyMMddHH(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHH"))

fun LocalDate.convertLocalDateTime(): LocalDateTime = LocalDateTime.of(this, LocalTime.MIN)
