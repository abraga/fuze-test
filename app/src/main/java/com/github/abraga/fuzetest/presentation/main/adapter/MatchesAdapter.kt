package com.github.abraga.fuzetest.presentation.main.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.github.abraga.fuzetest.data.models.MatchResponse

class MatchesAdapter(
    private val onItemClicked: (MatchResponse) -> Unit
) : PagingDataAdapter<MatchResponse, MatchesViewHolder>(MatchesDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it, onItemClicked) }
    }
}