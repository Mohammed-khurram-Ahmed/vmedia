package com.mohammed.khurram.vmediademo.remote

import com.mohammed.khurram.vmediademo.models.PokemonResponse
import com.mohammed.khurram.vmediademo.models.SinglePokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * remote interface for API end point
 */
interface RemoteAPI {

    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int
    ): SinglePokemonResponse
}