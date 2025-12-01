package com.malaguita.spotify_api.infrastructure.tables

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.timestamp

object ArtistsTable : Table("artistas") {
    val id = uuid("id").autoGenerate().uniqueIndex()

    val name = varchar("name", 100)
    val genre = varchar("genre", 50)

    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}