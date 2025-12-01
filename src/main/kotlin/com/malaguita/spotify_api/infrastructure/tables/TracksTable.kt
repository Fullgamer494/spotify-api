package com.malaguita.spotify_api.infrastructure.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object TracksTable : Table("tracks") {
    val id = uuid("id").autoGenerate().uniqueIndex()
    val title = varchar("title", 150)
    val duration = integer("duration")

    val albumId = uuid("album_id").references(AlbumsTable.id, onDelete = ReferenceOption.CASCADE)

    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}