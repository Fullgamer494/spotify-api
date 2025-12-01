package com.malaguita.spotify_api.application.dto

import com.malaguita.spotify_api.application.serializers.LocalDateTimeSerializer
import com.malaguita.spotify_api.application.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class CreateArtistDto(
    val name: String,
    val genre: String
)

@Serializable
data class UpdateArtistDto(
    val name: String?,
    val genre: String?
)

@Serializable
data class ArtistDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val genre: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime,
    val albumes: List<AlbumDto>? = null
)