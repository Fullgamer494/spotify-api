package com.malaguita.spotify_api.domain.models

import java.time.LocalDateTime
import java.util.UUID

data class Artist(
    val id: UUID,
    val name: String,
    val genre: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val albumes: List<Album>? = null
)