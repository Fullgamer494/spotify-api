package com.malaguita.spotify_api.infrastructure.routes

import com.malaguita.spotify_api.application.dto.CreateAlbumDto
import com.malaguita.spotify_api.application.dto.UpdateAlbumDto
import com.malaguita.spotify_api.application.services.AlbumApplicationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.albumRoutes(albumService: AlbumApplicationService) {
    route("/api/albumes") {
        
        // GET /api/albums
        get {
            try {
                val albums = albumService.findAll()
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // GET /api/albums/{id}
        get("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val withTracks = call.request.queryParameters["withTracks"]?.toBoolean() ?: false
                
                val album = albumService.findById(id, withTracks)
                if (album != null) {
                    call.respond(HttpStatusCode.OK, album)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Album not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // GET /api/albums/by-artist/{artistId}
        get("by-artist/{artistId}") {
            try {
                val artistId = call.parameters["artistId"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid artist ID format"))

                val albums = albumService.findByArtistId(artistId)
                call.respond(HttpStatusCode.OK, albums)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }

        // POST /api/albums
        post {
            try {
                val createAlbumDto = call.receive<CreateAlbumDto>()
                val createdAlbum = albumService.create(createAlbumDto)
                call.respond(HttpStatusCode.Created, createdAlbum)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // PUT /api/albums/{id}
        put("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val updateAlbumDto = call.receive<UpdateAlbumDto>()
                val updatedAlbum = albumService.update(id, updateAlbumDto)
                
                if (updatedAlbum != null) {
                    call.respond(HttpStatusCode.OK, updatedAlbum)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Album not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // DELETE /api/albums/{id}
        delete("{id}") {
            try {
                val id = call.parameters["id"]?.let { UUID.fromString(it) }
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid ID format"))

                val deleted = albumService.delete(id)
                if (deleted) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Album not found"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to e.message))
            }
        }
    }
}