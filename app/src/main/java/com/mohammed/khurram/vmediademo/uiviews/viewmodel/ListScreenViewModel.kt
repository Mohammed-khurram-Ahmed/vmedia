package com.mohammed.khurram.vmediademo.uiviews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mohammed.khurram.vmediademo.models.PokemonResult
import com.mohammed.khurram.vmediademo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var currentResult: Flow<PagingData<PokemonResult>>? = null

    /**
     * getData method to get data from pokemone API and return flow.
     * flow =  flow is a stream of multiple, asynchronously computed values.
     */
    fun getData(): Flow<PagingData<PokemonResult>> {
        val flow: Flow<PagingData<PokemonResult>> =
            repository.getPokemon().cachedIn(viewModelScope)
        currentResult = flow
        return flow
    }
}