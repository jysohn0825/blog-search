package com.jysohn0825.blog.config

import org.springframework.boot.test.context.TestConfiguration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@TestConfiguration
class TestConfig {

    @PersistenceContext
    lateinit var entityManager: EntityManager
}
