package com.github.abraga.fuzetest.presentation.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.abraga.fuzetest.data.models.PlayerResponse

class PlayersAdapter : ListAdapter<Pair<PlayerResponse?, PlayerResponse?>, PlayersViewHolder>(PlayersDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}