package com.malaguita.spotify_api.domain.models

import java.time.LocalDateTime
import java.util.UUID
import kotlin.time.Duration

data class Track(
    val id: UUID,
    val title: String,
    val duration: Int,
    val albumId: UUID,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)