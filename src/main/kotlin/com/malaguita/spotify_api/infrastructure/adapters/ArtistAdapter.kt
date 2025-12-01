package com.malaguita.spotify_api.infrastructure.adapters

import com.malaguita.spotify_api.domain.models.Artist
import com.malaguita.spotify_api.domain.ports.ArtistPort
import com.malaguita.spotify_api.infrastructure.adapters.mapper.toAlbumModel
import com.malaguita.spotify_api.infrastructure.adapters.mapper.toArtistModel
import com.malaguita.spotify_api.infrastructure.tables.AlbumsTable
import com.malaguita.spotify_api.infrastructure.tables.ArtistsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

class ArtistAdapter : ArtistPort {

    override fun findById(id: UUID, withAlbums: Boolean): Artist? = transaction {
        val artistRow = ArtistsTable.select { ArtistsTable.id eq id }.singleOrNull()
            ?: return@transaction null

        val artist = artistRow.toArtistModel()

        if (withAlbums) {
            val albums = AlbumsTable
                .select { AlbumsTable.artistId eq id }
                .map { it.toAlbumModel() }

            artist.copy(albumes = albums)
        } else {
            artist
        }
    }

    override fun findAll(): List<Artist> = transaction {
        ArtistsTable.selectAll().map { it.toArtistModel() }
    }

    override fun save(artist: Artist): Artist = transaction {
        ArtistsTable.insert {
            it[id] = artist.id
            it[name] = artist.name
            it[genre] = artist.genre
            it[createdAt] = Instant.ofEpochSecond(
                artist.createdAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
            it[updatedAt] = Instant.ofEpochSecond(
                artist.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
            )
        }
        artist
    }

    override fun update(artist: Artist): Artist = transaction {
        ArtistsTable.update(
            where = { ArtistsTable.id eq artist.id },
            body = {
                it[name] = artist.name
                it[genre] = artist.genre
                it[updatedAt] = Instant.ofEpochSecond(
                    artist.updatedAt.atZone(java.time.ZoneId.systemDefault()).toEpochSecond()
                )
            }
        )
        artist
    }

    override fun delete(id: UUID): Boolean = transaction {
        val deletedRows = ArtistsTable.deleteWhere(op = { ArtistsTable.id eq id })
        deletedRows > 0
    }
}