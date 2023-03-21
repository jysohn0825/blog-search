package com.jysohn0825.support.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getStringYyMMddHH(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHH"))
