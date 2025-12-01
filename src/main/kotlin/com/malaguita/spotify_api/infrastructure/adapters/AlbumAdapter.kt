package com.malaguita.spotify_api.infrastructure.adapters

import com.malaguita.spotify_api.domain.models.Album
import com.malaguita.spotify_api.domain.ports.AlbumPort
import com.malaguita.spotify_api.infrastructure.adapters.mapper.toAlbumModel
import com.malaguita.spotify_api.infrastructure.adapters.mapper.toTrackModel
import com.malaguita.spotify_api.infrastructure.tables.AlbumsTable
import com.malaguita.spotify_api.infrastructure.tables.TracksTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

class AlbumAdapter : AlbumPort {

    override fun findById(id: UUID, withTracks: Boolean): Album? = transaction {
        val albumRow = AlbumsTable.select { AlbumsTable.id eq id }.singleOrNull()
            ?: return@transaction null

        val album = albumRow.toAlbumModel()

        if (withTracks) {
            val tracks = TracksTable
                .select { TracksTable.albumId eq id }
                .map { it.toTrackModel() }

            album.copy(tracks = tracks)
        } else {
            album
        }
    }

    override fun findAll(): List<Album> = transaction {
        AlbumsTable.selectAll().map { it.toAlbumModel() }
    }

    override fun findByArtistId(artistId: UUID): List<Album> = transaction {
        AlbumsTable
            .select { AlbumsTable.artistId eq artistId }
            .map { it.toAlbumModel() }
    }

    override fun save(album: Album): Album = transaction {
        AlbumsTable.insert {
            it[id] = album.id
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[artistId] = album.artistId
            it[createdAt] = Instant.ofEpochSecond(
                album.createdAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
            it[updatedAt] = Instant.ofEpochSecond(
                album.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
        }
        album
    }

    override fun update(album: Album): Album = transaction {
        AlbumsTable.update({ AlbumsTable.id eq album.id }) {
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[updatedAt] = Instant.ofEpochSecond(
                album.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
        }
        album
    }

    override fun delete(id: UUID): Boolean = transaction {
        val deletedRows = AlbumsTable.deleteWhere { AlbumsTable.id eq id }
        deletedRows > 0
    }
}