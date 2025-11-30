package com.malaguita.spotify_api.domain.ports

import com.malaguita.spotify_api.domain.models.Album
import java.util.UUID

interface AlbumPort {
    fun findById(id: UUID, withTracks: Boolean = false): Album?
    fun findAll(): List<Album>
    fun findByArtistId(artistId: UUID): List<Album>
    fun save(album: Album): Album
    fun update(album: Album): Album
    fun delete(id: UUID): Boolean
}