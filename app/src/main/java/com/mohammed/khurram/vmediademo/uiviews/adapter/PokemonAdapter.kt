package com.mohammed.khurram.vmediademo.uiviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohammed.khurram.vmediademo.databinding.ViewPokemonListItemBinding
import com.mohammed.khurram.vmediademo.models.PokemonResult
import com.mohammed.khurram.vmediademo.utils.getImageUrl

/**
 * PagingDataAdapter class to bind data from data source to recycle view items
 */
class PokemonAdapter(private val navigate: (PokemonResult, String?) -> Unit) :
    PagingDataAdapter<PokemonResult, PokemonAdapter.ViewHolder>(
        PMDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)!!
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewPokemonListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ViewHolder(
        private val binding: ViewPokemonListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var image: String? = ""
        fun bind(pokemonResult: PokemonResult) {
            binding.apply {
                txtTitle.text = pokemonResult.name
                loadImage(this, pokemonResult)
                root.setOnClickListener {
                    navigate.invoke(pokemonResult, image)
                }
            }
        }

        private fun loadImage(binding: ViewPokemonListItemBinding, pokemonResult: PokemonResult) {
            image = pokemonResult.url.getImageUrl()
            binding.apply {
                Glide.with(root)
                    .load(image)
                    .into(imgPokemon)
            }
        }
    }

    private class PMDiffCallback : DiffUtil.ItemCallback<PokemonResult>() {
        override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem == newItem
        }
    }

}