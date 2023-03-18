package com.jysohn0825.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class BlogSearchApplication

fun main(args: Array<String>) {
    runApplication<BlogSearchApplication>(*args)
}
