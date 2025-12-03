package com.malaguita.spotify_api.infrastructure.routes

import com.malaguita.spotify_api.application.dto.CreateTrackDto
import com.malaguita.spotify_api.application.dto.UpdateTrackDto
import com.malaguita.spotify_api.application.services.TrackApplicationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.trackRoutes(trackService: TrackApplicationService) {
    route("/api/tracks") {
        
        // GET /api/tracks
        get {
            try {
                val tracks = trackService.findAll()
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // GET /api/tracks/{id}
        get("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val track = trackService.findById(id)
                if (track != null) {
                    call.respond(HttpStatusCode.OK, track)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // GET /api/tracks/by-album/{albumId}
        get("by-album/{albumId}") {
            try {
                val albumId = call.parameters["albumId"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid album ID format"))

                val tracks = trackService.findAllByAlbumId(albumId)
                call.respond(HttpStatusCode.OK, tracks)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // POST /api/tracks
        post {
            try {
                val createTrackDto = call.receive<CreateTrackDto>()
                val createdTrack = trackService.create(createTrackDto)
                call.respond(HttpStatusCode.Created, createdTrack)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // PUT /api/tracks/{id}
        put("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val updateTrackDto = call.receive<UpdateTrackDto>()
                val updatedTrack = trackService.update(id, updateTrackDto)
                
                if (updatedTrack != null) {
                    call.respond(HttpStatusCode.OK, updatedTrack)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // DELETE /api/tracks/{id}
        delete("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val deleted = trackService.delete(id)
                if (deleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Track not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
    }
}