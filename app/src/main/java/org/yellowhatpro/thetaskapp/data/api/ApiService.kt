package org.yellowhatpro.thetaskapp.data.api

import org.yellowhatpro.thetaskapp.data.entities.Song
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/songs")
    suspend fun getAllSongs() : Response<List<Song>>

}