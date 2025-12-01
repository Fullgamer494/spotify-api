package com.malaguita.spotify_api.infrastructure.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object AlbumsTable : Table("albumes") {
    val id = uuid("id").autoGenerate().uniqueIndex()
    val title = varchar("title", 150)
    val releaseYear = integer("release_year")

    val artistId = uuid("artist_id").references(ArtistsTable.id, onDelete = ReferenceOption.CASCADE)

    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}