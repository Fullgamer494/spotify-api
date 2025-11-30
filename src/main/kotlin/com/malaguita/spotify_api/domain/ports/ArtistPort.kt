package com.malaguita.spotify_api.domain.ports

import com.malaguita.spotify_api.domain.models.Artist
import java.util.UUID

interface ArtistPort {
    fun findById(id: UUID, withAlbums: Boolean = false): Artist?
    fun findAll(): List<Artist>
    fun save(artist: Artist): Artist
    fun update(artist: Artist): Artist
    fun delete(id: UUID): Boolean
}