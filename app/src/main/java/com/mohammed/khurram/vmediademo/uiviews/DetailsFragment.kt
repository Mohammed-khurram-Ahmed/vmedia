package com.mohammed.khurram.vmediademo.uiviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.mohammed.khurram.vmediademo.databinding.FragmentDetailsBinding
import com.mohammed.khurram.vmediademo.models.NetworkResult
import com.mohammed.khurram.vmediademo.models.PokemonResult
import com.mohammed.khurram.vmediademo.uiviews.viewmodel.DetailsViewModel
import com.mohammed.khurram.vmediademo.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

/**
 * Requirements :
 * Have the ability to view detailed information about the Pokémon.
 * To do this, make a separate screen, which can be accessed by clicking on any item in the list of Pokémons.
 * Detailed information should at a minimum include the following fields:
 * height, weight, Pokémon genus (type, e.g. bird, insect, venomous, etc.), information (stats; include attack, defense, hp fields).
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    /*
     * binding object for xml views and kotlin code
     */
    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    /**
     * Android fragment lifecycle  callbacks
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    /**
     * Android fragment lifecycle  callbacks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
         * get data from previous screen from parcelable
          */
        val pokemonResult = arguments?.getParcelable<PokemonResult>(AppConstants.DATA)
        loadSinglePokemon(pokemonResult!!)
    }

    /**
     * get details of current selected pokemon , and populate toUI
     */
    private fun loadSinglePokemon(pokemonResult: PokemonResult) {
        binding.txtTitle.text = pokemonResult.name.capitalize()
        Glide.with(requireContext())
            .load(pokemonResult.actualUrl)
            .into(binding.imgPokemon)
        viewModel.getSinglePokemon(pokemonResult.url).observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    binding.txtBodyDetails.text =
                        "Height ${it.data?.height} - Weight ${it.data?.weight}"
                    var stat = "Stat: \n"
                    binding.apply {
                        it.data?.stats?.forEach {

                            val name = it.stat.name.capitalize()
                            val base = it.base_stat.toString()
                            stat += "$name - $base \n"

                        }
                        txtStateDetails.text = stat
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        context,
                        "Something went wrong...Try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

}