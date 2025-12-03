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
        val database = Database.connect(createHikariDataSource())
        
        transaction(database) {
            SchemaUtils.create(ArtistsTable, AlbumsTable, TracksTable)
        }
    }

    private fun createHikariDataSource() = HikariDataSource(HikariConfig().apply {
        driverClassName = AppConfig.dbDriver
        jdbcUrl = AppConfig.dbUrl
        username = AppConfig.dbUser
        password = AppConfig.dbPassword
        maximumPoolSize = AppConfig.dbMaxPoolSize
        isAutoCommit = AppConfig.dbAutoCommit
        transactionIsolation = AppConfig.dbTransactionIsolation
        validate()
    })
}