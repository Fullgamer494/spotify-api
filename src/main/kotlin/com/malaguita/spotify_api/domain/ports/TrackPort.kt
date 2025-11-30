package com.malaguita.spotify_api.domain.ports

import com.malaguita.spotify_api.domain.models.Track
import java.util.UUID

interface TrackPort {
    fun findById(id: UUID): Track?
    fun findAll(): List<Track>
    fun findAllByAlbumId(albumId: UUID): List<Track>
    fun save(track: Track): Track
    fun update(track: Track): Track
    fun delete(id: UUID): Boolean
}