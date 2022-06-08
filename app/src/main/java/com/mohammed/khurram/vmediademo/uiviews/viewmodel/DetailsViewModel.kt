package com.mohammed.khurram.vmediademo.uiviews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammed.khurram.vmediademo.models.NetworkResult
import com.mohammed.khurram.vmediademo.models.SinglePokemonResponse
import com.mohammed.khurram.vmediademo.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _liveData = MutableLiveData<NetworkResult<SinglePokemonResponse>>()

    /**
     * Get details for single pokemon, extract pokemon id from existing list item url
     */
    fun getSinglePokemon(url: String) :  LiveData<NetworkResult<SinglePokemonResponse>>{
        viewModelScope.launch {
            val id = url.substringAfter("pokemon").replace("/", "").toInt()
            val result = repository.getSinglePokemon(id)
            _liveData.postValue(result)
        }
        return _liveData
    }
}