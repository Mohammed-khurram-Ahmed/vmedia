package com.mohammed.khurram.vmediademo.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mohammed.khurram.vmediademo.models.PokemonResult
import com.mohammed.khurram.vmediademo.utils.AppConstants.LOADER_DELAY
import com.mohammed.khurram.vmediademo.utils.AppConstants.START
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * defining a PokemonPagingSource, and emitting PagingData LoadResult
 */
class PokemonPagingSource(private val remoteAPI: RemoteAPI) :
    PagingSource<Int, PokemonResult>() {
    /**
     * This method is called when the Paging library needs to reload items for the UI
     */
    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition
    }

    /**
     * The load() function will be called by the Paging library to asynchronously fetch more data to be displayed as the user scrolls around
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val offset = params.key ?: START
        val loadSize = params.loadSize
        val start = params.key ?: START
        if (start != START) delay(LOADER_DELAY)
        return try {
            val data = remoteAPI.getPokemons(loadSize, offset)
            //If returning null there is no more to fetch
            LoadResult.Page(
                data = data.results,
                prevKey = if (offset == START) null else offset - loadSize,
                nextKey = if (data.next == null) null else offset + loadSize
            )
        } catch (t: Throwable) {
            var exception = t
            if (t is IOException) {
                exception = IOException("Please connect internet, and try again")
            }
            LoadResult.Error(exception)
        }
    }
}