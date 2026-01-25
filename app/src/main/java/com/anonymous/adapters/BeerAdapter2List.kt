package com.anonymous.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anonymous.data.Beer
import com.anonymous.databinding.ItemBeerBinding

class BeerAdapter2List(
    private val onItemClick: (Beer) -> Unit,
    private val onDeleteClick: (Beer) -> Unit
) : ListAdapter<Beer, BeerAdapter2List.BeerViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerViewHolder {
        val binding = ItemBeerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BeerViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class BeerViewHolder(
        private val binding: ItemBeerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer) = with(binding) {
            tvName.text = beer.name
            tvType.text = beer.type.name
            tvRating.text = "⭐ ${beer.rating}/5"
            tvNote.text = beer.note

            btnEdit.setOnClickListener {
                onItemClick(beer)
            }

            btnDelete.setOnClickListener {
                onDeleteClick(beer)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Beer>() {

            override fun areItemsTheSame(
                oldItem: Beer,
                newItem: Beer
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Beer,
                newItem: Beer
            ): Boolean = oldItem == newItem
        }
    }
}