package com.malaguita

import com.malaguita.spotify_api.application.services.*
import com.malaguita.spotify_api.infrastructure.adapters.*
import com.malaguita.spotify_api.infrastructure.config.DatabaseFactory
import com.malaguita.spotify_api.infrastructure.routes.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.module() {
    // Inicializar base de datos
    DatabaseFactory.init()
    
    // Configurar JSON serialization
    install(ContentNegotiation) {
        json()
    }

    // Crear instancias de adapters
    val artistAdapter = ArtistAdapter()
    val albumAdapter = AlbumAdapter()
    val trackAdapter = TrackAdapter()
    
    // Crear instancias de servicios
    val artistService = ArtistApplicationService(artistAdapter)
    val albumService = AlbumApplicationService(albumAdapter)
    val trackService = TrackApplicationService(trackAdapter)

    // Configurar routing
    routing {
        artistRoutes(artistService)
        albumRoutes(albumService)
        trackRoutes(trackService)
    }
}