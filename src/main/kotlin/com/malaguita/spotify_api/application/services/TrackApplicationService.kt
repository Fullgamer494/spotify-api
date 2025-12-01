package com.malaguita.spotify_api.application.services

import com.malaguita.spotify_api.application.dto.*
import com.malaguita.spotify_api.domain.models.Track
import com.malaguita.spotify_api.domain.ports.TrackPort
import java.time.LocalDateTime
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class TrackApplicationService(
    private val trackPort: TrackPort
) {

    fun findById(id: UUID): TrackDto? {
        return trackPort.findById(id)?.toDto()
    }

    fun findAll(): List<TrackDto> {
        return trackPort.findAll().map { it.toDto() }
    }

    fun findAllByAlbumId(albumId: UUID): List<TrackDto> {
        return trackPort.findAllByAlbumId(albumId).map { it.toDto() }
    }

    fun create(createTrackDto: CreateTrackDto): TrackDto {
        val now = LocalDateTime.now()
        val track = Track(
            id = UUID.randomUUID(),
            title = createTrackDto.title,
            duration = createTrackDto.duration.toInt(),
            albumId = createTrackDto.albumId,
            createdAt = now,
            updatedAt = now
        )
        return trackPort.save(track).toDto()
    }

    fun update(id: UUID, updateTrackDto: UpdateTrackDto): TrackDto? {
        val existingTrack = trackPort.findById(id) ?: return null

        val updatedTrack = existingTrack.copy(
            title = updateTrackDto.title ?: existingTrack.title,
            duration = updateTrackDto.duration?.toInt() ?: existingTrack.duration, // Convertir Int a Duration
            updatedAt = LocalDateTime.now()
        )

        return trackPort.update(updatedTrack).toDto()
    }

    fun delete(id: UUID): Boolean {
        return trackPort.delete(id)
    }

    private fun Track.toDto(): TrackDto {
        return TrackDto(
            id = this.id,
            title = this.title,
            duration = this.duration.toInt(),
            albumId = this.albumId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}