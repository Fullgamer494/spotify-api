package com.malaguita.spotify_api.infrastructure.config

import com.malaguita.spotify_api.infrastructure.tables.AlbumsTable
import com.malaguita.spotify_api.infrastructure.tables.ArtistsTable
import com.malaguita.spotify_api.infrastructure.tables.TracksTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/spotify"
        val user = "postgres"
        val password = "qwerty123"

        val database = Database.connect(createHikariDataSource(jdbcURL, driverClassName, user, password))
        
        transaction(database) {
            SchemaUtils.create(ArtistsTable, AlbumsTable, TracksTable)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
        user: String,
        password: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        username = user
        this.password = password
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })
}