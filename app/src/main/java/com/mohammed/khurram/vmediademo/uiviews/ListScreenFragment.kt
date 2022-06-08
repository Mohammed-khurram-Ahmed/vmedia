package com.mohammed.khurram.vmediademo.uiviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohammed.khurram.vmediademo.R
import com.mohammed.khurram.vmediademo.databinding.FragmentListBinding
import com.mohammed.khurram.vmediademo.models.PokemonResult
import com.mohammed.khurram.vmediademo.uiviews.adapter.PokemonAdapter
import com.mohammed.khurram.vmediademo.uiviews.viewmodel.ListScreenViewModel
import com.mohammed.khurram.vmediademo.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListScreenFragment : Fragment() {
    lateinit var binding: FragmentListBinding
    lateinit var viewModel: ListScreenViewModel
    lateinit var manager: LinearLayoutManager
    private var job: Job? = null
    private val adapter =
        PokemonAdapter { pokemonResult: PokemonResult, actualUrl: String? ->
            pokemonResult.actualUrl = actualUrl
            navigateToDetails(pokemonResult)
        }

    /**
     * Android fragment lifecycle  callbacks
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Android fragment lifecycle  callbacks
     */
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
         * View Model Setup
         */
        viewModel = ViewModelProvider(this)[ListScreenViewModel::class.java]

        /*
         * RecyclerView setup
         */
        manager = LinearLayoutManager(context);
        binding.rvPokemons.layoutManager = manager
        binding.rvPokemons.adapter = adapter

        /*
         * Show loader on fetching data
         */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    binding.progressCircular.isVisible = it.source.prepend is LoadState.Loading
                    binding.progressCircular.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
        /*
        * Reload button to get data again
        */
        binding.fabRefreshBtn.setOnClickListener {
            startFetchingPokemon( true)
        }
        startFetchingPokemon( true)
    }

    /**
     * fetch data fron PokemonPagingSource and load to UI list
     */
    private fun startFetchingPokemon(shouldSubmitEmpty: Boolean) {
        job?.cancel()
        job = lifecycleScope.launch {
            if (shouldSubmitEmpty) adapter.submitData(PagingData.empty())
            viewModel.getData().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /**
     * Navigation method , data pass to details screen fragment
     */
    private fun navigateToDetails(pokemonResult: PokemonResult) {
        findNavController().navigate(
            R.id.action_FirstFragment_to_SecondFragment,
            Bundle().apply { putParcelable(AppConstants.DATA, pokemonResult) }
        )
    }

}