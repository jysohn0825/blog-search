package com.jysohn0825.blog.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@EnableTransactionManagement
@Configuration
class DataSourceConfig {

    @Bean(name = ["dataSource"])
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    fun dataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean("transactionManager")
    internal fun transactionManager(factory: EntityManagerFactory) =
        JpaTransactionManager(factory).apply { entityManagerFactory = factory }
}
