package com.mohammed.khurram.vmediademo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mohammed.khurram.vmediademo.models.NetworkResult
import com.mohammed.khurram.vmediademo.models.SinglePokemonResponse
import com.mohammed.khurram.vmediademo.remote.PokemonPagingSource
import com.mohammed.khurram.vmediademo.remote.RemoteAPI
import com.mohammed.khurram.vmediademo.utils.AppConstants.SEARCH_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class to gather data from different data sources, like remote data source or local datasource
 * here we are using only from Remote data source for our VMedia Example
 */
class Repository @Inject constructor(
    private val remoteInterface: RemoteAPI
) {
    /**
     * This function get pokemon list from PokemonPagingSource and return flow object
     * Also it takes configuration of PokemonPagingSource like page size etc.
     * return  flow ,  flow is a stream of multiple, asynchronously computed values.
     */
    fun getPokemon() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = SEARCH_SIZE),
        pagingSourceFactory = {
            PokemonPagingSource(remoteInterface)
        }
    ).flow

    /**
     * This Function will used to get details of Single Pokemon at details Screen
     */
    suspend fun getSinglePokemon(id: Int): NetworkResult<SinglePokemonResponse> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext NetworkResult.Success(remoteInterface.getSinglePokemon(id))
            } catch (ex: Exception) {
                return@withContext NetworkResult.Error("Got Some Error, Please ry again")
            }
        }

    }

}