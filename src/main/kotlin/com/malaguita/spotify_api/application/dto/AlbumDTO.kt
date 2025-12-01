package com.malaguita.spotify_api.application.dto

import com.malaguita.spotify_api.application.serializers.LocalDateTimeSerializer
import com.malaguita.spotify_api.application.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class CreateAlbumDto(
    val title: String,
    val releaseYear: Int,
    @Serializable(with = UUIDSerializer::class)
    val artistId: UUID
)

@Serializable
data class UpdateAlbumDto(
    val title: String?,
    val releaseYear: Int?
)

@Serializable
data class AlbumDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val releaseYear: Int,
    @Serializable(with = UUIDSerializer::class)
    val artistId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime,
    val tracks: List<TrackDto>? = null
)