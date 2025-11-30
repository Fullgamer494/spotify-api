package com.malaguita.spotify_api.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class Album(
    val id: UUID,
    val title: String,
    val releaseYear: Int,
    val artistId: UUID,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val tracks: List<Track>? = null
)