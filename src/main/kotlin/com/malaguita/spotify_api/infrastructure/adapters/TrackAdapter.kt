package com.malaguita.spotify_api.infrastructure.adapters

import com.malaguita.spotify_api.domain.models.Track
import com.malaguita.spotify_api.domain.ports.TrackPort
import com.malaguita.spotify_api.infrastructure.adapters.mapper.toTrackModel
import com.malaguita.spotify_api.infrastructure.tables.TracksTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

class TrackAdapter : TrackPort {

    override fun findById(id: UUID): Track? = transaction {
        TracksTable.select { TracksTable.id eq id }
            .singleOrNull()
            ?.toTrackModel()
    }

    override fun findAll(): List<Track> = transaction {
        TracksTable.selectAll().map { it.toTrackModel() }
    }

    override fun findAllByAlbumId(albumId: UUID): List<Track> = transaction {
        TracksTable
            .select { TracksTable.albumId eq albumId }
            .map { it.toTrackModel() }
    }

    override fun save(track: Track): Track = transaction {
        TracksTable.insert {
            it[id] = track.id
            it[title] = track.title
            it[duration] = track.duration.toInt()
            it[albumId] = track.albumId
            it[createdAt] = Instant.ofEpochSecond(
                track.createdAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
            it[updatedAt] = Instant.ofEpochSecond(
                track.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
        }
        track
    }

    override fun update(track: Track): Track = transaction {
        TracksTable.update({ TracksTable.id eq track.id }) {
            it[title] = track.title
            it[duration] = track.duration.toInt()
            it[updatedAt] = Instant.ofEpochSecond(
                track.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
        }
        track
    }

    override fun delete(id: UUID): Boolean = transaction {
        val deletedRows = TracksTable.deleteWhere { TracksTable.id eq id }
        deletedRows > 0
    }
}