package com.malaguita.spotify_api.infrastructure.adapters.mapper

import com.malaguita.spotify_api.domain.models.Album
import com.malaguita.spotify_api.domain.models.Artist
import com.malaguita.spotify_api.domain.models.Track
import com.malaguita.spotify_api.infrastructure.tables.AlbumsTable
import com.malaguita.spotify_api.infrastructure.tables.ArtistsTable
import com.malaguita.spotify_api.infrastructure.tables.TracksTable
import org.jetbrains.exposed.sql.ResultRow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.Duration.Companion.seconds

private val toLocalDateTime = { instant: Instant ->
    LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun ResultRow.toArtistModel(): Artist {
    return Artist(
        id = this[ArtistsTable.id],
        name = this[ArtistsTable.name],
        genre = this[ArtistsTable.genre],
        createdAt = toLocalDateTime(this[ArtistsTable.createdAt]),
        updatedAt = toLocalDateTime(this[ArtistsTable.updatedAt]),
        albumes = null
    )
}

fun ResultRow.toAlbumModel(): Album {
    return Album(
        id = this[AlbumsTable.id],
        title = this[AlbumsTable.title],
        releaseYear = this[AlbumsTable.releaseYear],
        artistId = this[AlbumsTable.artistId],
        createdAt = toLocalDateTime(this[AlbumsTable.createdAt]),
        updatedAt = toLocalDateTime(this[AlbumsTable.updatedAt]),
        tracks = null
    )
}

fun ResultRow.toTrackModel(): Track {
    return Track(
        id = this[TracksTable.id],
        title = this[TracksTable.title],
        duration = this[TracksTable.duration],
        albumId = this[TracksTable.albumId],
        createdAt = toLocalDateTime(this[TracksTable.createdAt]),
        updatedAt = toLocalDateTime(this[TracksTable.updatedAt])
    )
}