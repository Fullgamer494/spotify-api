package com.malaguita.spotify_api.infrastructure.routes

import com.malaguita.spotify_api.application.dto.CreateArtistDto
import com.malaguita.spotify_api.application.dto.UpdateArtistDto
import com.malaguita.spotify_api.application.services.ArtistApplicationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.artistRoutes(artistService: ArtistApplicationService) {
    route("/api/artistas") {
        
        // GET /api/artists
        get {
            try {
                val artists = artistService.findAll()
                call.respond(HttpStatusCode.OK, artists)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // GET /api/artists/{id}
        get("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val withAlbums = call.request.queryParameters["withAlbums"]?.toBoolean() ?: false
                
                val artist = artistService.findById(id, withAlbums)
                if (artist != null) {
                    call.respond(HttpStatusCode.OK, artist)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artist not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // POST /api/artists
        post {
            try {
                val createArtistDto = call.receive<CreateArtistDto>()
                val createdArtist = artistService.create(createArtistDto)
                call.respond(HttpStatusCode.Created, createdArtist)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // PUT /api/artists/{id}
        put("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val updateArtistDto = call.receive<UpdateArtistDto>()
                val updatedArtist = artistService.update(id, updateArtistDto)
                
                if (updatedArtist != null) {
                    call.respond(HttpStatusCode.OK, updatedArtist)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artist not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // DELETE /api/artists/{id}
        delete("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val deleted = artistService.delete(id)
                if (deleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Artist not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
    }
}