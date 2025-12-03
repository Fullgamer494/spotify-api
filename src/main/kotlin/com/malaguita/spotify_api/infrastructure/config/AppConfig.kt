package com.malaguita.spotify_api.infrastructure.config

import io.github.cdimascio.dotenv.dotenv

object AppConfig {
    private val dotenv = dotenv {
        directory = "./"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

    val dbDriver: String = dotenv["DB_DRIVER"] ?: "org.postgresql.Driver"
    val dbUrl: String = dotenv["DB_URL"] ?: "jdbc:postgresql://localhost:5432/spotify"
    val dbUser: String = dotenv["DB_USER"] ?: "postgres"
    val dbPassword: String = dotenv["DB_PASSWORD"] ?: "defaultpassword"
    val dbMaxPoolSize: Int = dotenv["DB_MAX_POOL_SIZE"]?.toIntOrNull() ?: 3
    val dbAutoCommit: Boolean = dotenv["DB_AUTO_COMMIT"]?.toBoolean() ?: false
    val dbTransactionIsolation: String = dotenv["DB_TRANSACTION_ISOLATION"] ?: "TRANSACTION_REPEATABLE_READ"
}