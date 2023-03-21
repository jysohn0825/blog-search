package com.jysohn0825.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class BlogSearchApplication

fun main(args: Array<String>) {
    runApplication<BlogSearchApplication>(*args)
}
