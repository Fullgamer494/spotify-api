package com.malaguita.spotify_api.application.services

import com.malaguita.spotify_api.application.dto.*
import com.malaguita.spotify_api.domain.models.Artist
import com.malaguita.spotify_api.domain.ports.ArtistPort
import java.time.LocalDateTime
import java.util.UUID

class ArtistApplicationService(
    private val artistPort: ArtistPort
) {

    fun findById(id: UUID, withAlbums: Boolean = false): ArtistDto? {
        return artistPort.findById(id, withAlbums)?.toDto()
    }

    fun findAll(): List<ArtistDto> {
        return artistPort.findAll().map { it.toDto() }
    }

    fun create(createArtistDto: CreateArtistDto): ArtistDto {
        val now = LocalDateTime.now()
        val artist = Artist(
            id = UUID.randomUUID(),
            name = createArtistDto.name,
            genre = createArtistDto.genre,
            createdAt = now,
            updatedAt = now
        )
        return artistPort.save(artist).toDto()
    }

    fun update(id: UUID, updateArtistDto: UpdateArtistDto): ArtistDto? {
        val existingArtist = artistPort.findById(id) ?: return null
        
        val updatedArtist = existingArtist.copy(
            name = updateArtistDto.name ?: existingArtist.name,
            genre = updateArtistDto.genre ?: existingArtist.genre,
            updatedAt = LocalDateTime.now()
        )
        
        return artistPort.update(updatedArtist).toDto()
    }

    fun delete(id: UUID): Boolean {
        return artistPort.delete(id)
    }

    private fun Artist.toDto(): ArtistDto {
        return ArtistDto(
            id = this.id,
            name = this.name,
            genre = this.genre,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            albumes = this.albumes?.map { album ->
                AlbumDto(
                    id = album.id,
                    title = album.title,
                    releaseYear = album.releaseYear,
                    artistId = album.artistId,
                    createdAt = album.createdAt,
                    updatedAt = album.updatedAt
                )
            }
        )
    }
}