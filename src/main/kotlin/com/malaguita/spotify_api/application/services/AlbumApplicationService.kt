package com.malaguita.spotify_api.application.services

import com.malaguita.spotify_api.application.dto.*
import com.malaguita.spotify_api.domain.models.Album
import com.malaguita.spotify_api.domain.ports.AlbumPort
import java.time.LocalDateTime
import java.util.UUID

class AlbumApplicationService(
    private val albumPort: AlbumPort
) {

    fun findById(id: UUID, withTracks: Boolean = false): AlbumDto? {
        return albumPort.findById(id, withTracks)?.toDto()
    }

    fun findAll(): List<AlbumDto> {
        return albumPort.findAll().map { it.toDto() }
    }

    fun findByArtistId(artistId: UUID): List<AlbumDto> {
        return albumPort.findByArtistId(artistId).map { it.toDto() }
    }

    fun create(createAlbumDto: CreateAlbumDto): AlbumDto {
        val now = LocalDateTime.now()
        val album = Album(
            id = UUID.randomUUID(),
            title = createAlbumDto.title,
            releaseYear = createAlbumDto.releaseYear,
            artistId = createAlbumDto.artistId, // Ya es UUID
            createdAt = now,
            updatedAt = now
        )
        return albumPort.save(album).toDto()
    }

    fun update(id: UUID, updateAlbumDto: UpdateAlbumDto): AlbumDto? {
        val existingAlbum = albumPort.findById(id) ?: return null
        
        val updatedAlbum = existingAlbum.copy(
            title = updateAlbumDto.title ?: existingAlbum.title,
            releaseYear = updateAlbumDto.releaseYear ?: existingAlbum.releaseYear,
            updatedAt = LocalDateTime.now()
        )
        
        return albumPort.update(updatedAlbum).toDto()
    }

    fun delete(id: UUID): Boolean {
        return albumPort.delete(id)
    }

    private fun Album.toDto(): AlbumDto {
        return AlbumDto(
            id = this.id,
            title = this.title,
            releaseYear = this.releaseYear,
            artistId = this.artistId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            tracks = this.tracks?.map { track ->
                TrackDto(
                    id = track.id,
                    title = track.title,
                    duration = track.duration,
                    albumId = track.albumId,
                    createdAt = track.createdAt,
                    updatedAt = track.updatedAt
                )
            }
        )
    }
}