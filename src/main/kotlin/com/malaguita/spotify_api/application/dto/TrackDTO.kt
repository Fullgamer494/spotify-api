package com.malaguita.spotify_api.application.dto

import com.malaguita.spotify_api.application.serializers.LocalDateTimeSerializer
import com.malaguita.spotify_api.application.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class CreateTrackDto(
    val title: String,
    val duration: Int,
    @Serializable(with = UUIDSerializer::class)
    val albumId: UUID
)

@Serializable
data class UpdateTrackDto(
    val title: String?,
    val duration: Int? // Formato ISO 8601
)

@Serializable
data class TrackDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val duration: Int,
    @Serializable(with = UUIDSerializer::class)
    val albumId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)